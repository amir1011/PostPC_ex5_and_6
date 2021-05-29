package exercise.android.reemh.todo_items

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


// TODO: implement!
class TodoItemsHolderImpl(private var context: Context) : TodoItemsHolder {
    override var currentItems: MutableList<TodoItem>? = null
        get() {
            if(field == null) currentItems = mutableListOf()
            return field
        }
    private val curContext: Context =  context
    private val sp: SharedPreferences = curContext.getSharedPreferences("local_db_todoItems", Context.MODE_PRIVATE)
    private val todoLiveDataMutable: MutableLiveData<MutableList<TodoItem>> =
            MutableLiveData<MutableList<TodoItem>>()
    override val todoLiveDataPublic: LiveData<MutableList<TodoItem>> = todoLiveDataMutable

    init {
        val keys: Set<String>  = sp.all.keys
        for (key: String in keys)
        {
            val todoItemAsString: String? = sp.getString(key, null)
            if(todoItemAsString != null)
            {
                val todoItem: TodoItem = fromStringToItem(todoItemAsString)
                currentItems?.add(todoItem)
            }
        }
        this.todoLiveDataMutable.value = ArrayList(currentItems)
    }

    fun getCopies(): MutableList<TodoItem>{
        val copyList = mutableListOf<TodoItem>()
        copyList.addAll(currentItems!!)
        return copyList
    }

    private fun fromItemToString(item: TodoItem): String{
        val boolInt = if(item.taskIsDone) 1 else 0
        val text = item.taskText
        val creationDate = item.creationDate
        val modData = item.lastModified.toString()
        val itemId: String = item.idString
        return "$boolInt#$text#$creationDate#$modData#$itemId"
    }


    private fun stringToDate(aDate: String? , aFormat: String ): Date? {

        if(aDate==null) return null
        val pos: ParsePosition  = ParsePosition(0)
        val simpledateformat: SimpleDateFormat  = SimpleDateFormat(aFormat)
        val stringDate: Date  = simpledateformat.parse(aDate, pos)!!
        return stringDate

    }

    private fun fromStringToItem(str: String): TodoItem{
//        try{
            val tempList : List<String> = str.split("#")
            val todoItem: TodoItem = TodoItem()
            todoItem.setDate(tempList[2])
            todoItem.setText(tempList[1])
            val expiredDate: Date? = stringToDate(tempList[3], "EEE MMM d HH:mm:ss zz yyyy")
            todoItem.modifyDate(expiredDate)
            todoItem.setID(tempList[4])
            todoItem.taskIsDone = tempList[0]== "1"
            return todoItem
//        } catch (e: Exception)
//        {
//            System.err.println("Invalid string format: $e")
//        }
    }



//    @RequiresApi(Build.VERSION_CODES.O)
    override fun addNewInProgressItem(description: String?)
    {
        val todoItem: TodoItem = TodoItem()
        val newId: String = UUID.randomUUID().toString()
        val dateCreation: Date = Calendar.getInstance().time
        todoItem.setText(description)
        todoItem.setID(newId)
        todoItem.setDate(dateCreation.toString())
        todoItem.modifyDate(dateCreation)
        if(currentItems == null)
            currentItems = mutableListOf<TodoItem>(todoItem)
        else
            currentItems!!.add(0, todoItem)
        todoLiveDataMutable.value = ArrayList(currentItems)
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString(todoItem.idString, fromItemToString(todoItem))
        editor.apply()
    }
    override fun markItemDone(item: TodoItem?) {
        if (item == null)
            return
//        item.setState(true)

        val editedTask: TodoItem = TodoItem()
        editedTask.setText(item.taskText)
        editedTask.setID(item.idString)
        editedTask.setDate(item.creationDate)
        editedTask.modifyDate(item.lastModified)
        editedTask.setState(true)
        currentItems!!.remove(item)
        currentItems!!.add(editedTask)

        todoLiveDataMutable.value = ArrayList(currentItems)

        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString(item.idString, fromItemToString(item))
        editor.apply()
    }
    override fun markItemInProgress(item: TodoItem?) {
        if (item == null)
            return
//        item.setState(false)

        val editedTask: TodoItem = TodoItem()
        editedTask.setText(item.taskText)
        editedTask.setID(item.idString)
        editedTask.setDate(item.creationDate)
        editedTask.modifyDate(item.lastModified)
        editedTask.setState(false)
        currentItems!!.remove(item)
        currentItems!!.add(0, editedTask)

        todoLiveDataMutable.value = ArrayList(currentItems)

        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString(item.idString, fromItemToString(item))
        editor.apply()
    }
//    override fun deleteItem(item: TodoItem?) {
//        if(item == null || currentItems == null)
//           return
//        currentItems!!.remove(item)
//    }

    override fun getItem(index: Int): TodoItem? {
        return currentItems?.getOrNull(index)
    }

    private fun getById(id:String): TodoItem? {
        for (item: TodoItem in currentItems!!)
        {
            if(item.idString == id) return item
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun editName(id: String, newName: String){
        val oldItem: TodoItem = getById(id) ?: return
        val editedTask: TodoItem = TodoItem()
        editedTask.setText(newName)
        editedTask.setState(oldItem.taskIsDone)
        editedTask.setID(oldItem.idString)
        editedTask.setDate(oldItem.creationDate)
        editedTask.modifyDate(Calendar.getInstance().time)
        currentItems!!.remove(oldItem)
        currentItems!!.add(0, editedTask)
        todoLiveDataMutable.value = ArrayList(currentItems)

        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString(editedTask.idString, fromItemToString(editedTask))
        editor.apply()
    }

    override fun deleteItem(id: String){
        val itemToRemove: TodoItem = getById(id) ?: return
        currentItems!!.remove(itemToRemove)
        todoLiveDataMutable.value = ArrayList(currentItems)

        val editor: SharedPreferences.Editor = sp.edit()
        editor.remove(itemToRemove.idString)
        editor.apply()

        todoLiveDataMutable.value = ArrayList(currentItems)
    }

}