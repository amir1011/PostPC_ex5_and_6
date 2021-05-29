package exercise.android.reemh.todo_items

import java.io.Serializable
import java.util.*

class TodoItem : Serializable { // TODO: edit this class as you want
    var taskIsDone: Boolean = false
    var taskText: String = ""
    var creationDate: String = ""
    var lastModified: Date? = null
    var idString: String = ""

    fun setID(id: String?)
    {
        if(id == null)
            return
        idString = id
    }

    fun modifyDate(time: Date?)
    {
        if(time == null)
            return
        lastModified = time
    }

    fun setText(newText: String?)
    {
        if(newText == null)
            return
        taskText = newText
    }

    fun setDate(newDate: String?)
    {
        if(newDate == null)
            return
        creationDate = newDate
    }

//    fun fromItemToString(item: TodoItem): String{
//        val boolInt = if(item.taskIsDone) 1 else 0
//        return "$boolInt#$taskText#$creationDate"
//    }
//
//    fun fromStringToItem(str: String): TodoItem{
//        val tempList : List<String> = str.split("#")
//        val todoItem: TodoItem = TodoItem()
//        todoItem.setDate(tempList[2])
//        todoItem.setText(tempList[1])
//        todoItem.taskIsDone = tempList[0]== "1"
//        return todoItem
//    }

    fun setState(setState : Boolean)
    {
        taskIsDone = setState
    }
}