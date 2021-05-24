package exercise.android.reemh.todo_items

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    @JvmField
    var holder: TodoItemsHolder? = null
//    private var adapter: ItemAdapter? = null
    private var currText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (holder == null) {
            holder = TodoItemsHolderImpl()
        }

        // TODO: implement the specs as defined below
        //    (find all UI components, hook them up, connect everything you need)

        val newTodoItem = findViewById<FloatingActionButton>(R.id.buttonCreateTodoItem)
        currText = findViewById<EditText>(R.id.editTextInsertTask)
        val recyclerTodosView = findViewById<RecyclerView>(R.id.recyclerTodoItemsList)

        val adapter = ItemAdapter()
        adapter.setTodoItems(holder!!)

        recyclerTodosView.adapter = adapter
        recyclerTodosView.layoutManager = LinearLayoutManager(this,
                RecyclerView.VERTICAL, false)

        adapter.onTodoItemCheckBoxClickCallback = { curItem ->
            if (!curItem.taskIsDone){
                holder!!.markItemDone(curItem)
                holder!!.currentItems?.remove(curItem)
                holder!!.currentItems?.add(curItem)
            }
            else{
                holder!!.markItemInProgress(curItem)
                holder!!.currentItems?.remove(curItem)
                holder!!.currentItems?.add(0, curItem)
            }
            adapter.notifyDataSetChanged()
        }
        adapter.onTodoItemDeleteClickCallback = { curItem ->
            holder!!.deleteItem(curItem)
            adapter.notifyDataSetChanged()
        }
        newTodoItem.setOnClickListener{
            if(currText!!.text.toString() == "")
                return@setOnClickListener
//            findViewById<EditText>(R.id.todoItemText).setText(currText!!.text.toString())
            holder!!.addNewInProgressItem(currText!!.text.toString())
            adapter.notifyDataSetChanged()
            currText!!.setText("")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currText = findViewById<EditText>(R.id.editTextInsertTask)

        outState.putSerializable("items_holder", holder?.currentItems?.toTypedArray())
//        outState.putSerializable("adapter", adapter)
        outState.putString("curr_text", currText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val array: Array<TodoItem> = savedInstanceState.getSerializable("items_holder") as Array<TodoItem>
        holder?.currentItems = array.toMutableList();
//        adapter = savedInstanceState.getSerializable("adapter") as ItemAdapter?
        currText!!.setText(savedInstanceState.getString("curr_text"))
    }
}