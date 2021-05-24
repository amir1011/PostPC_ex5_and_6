package exercise.android.reemh.todo_items

import android.content.ClipData
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class ItemAdapter: RecyclerView.Adapter<ItemViewHolder>(), Serializable {
    private var _todoItemsHolder: TodoItemsHolder? = null
    public var onTodoItemDeleteClickCallback: ((TodoItem) -> Unit)? = null
    public var onTodoItemCheckBoxClickCallback: ((TodoItem) -> Unit)? = null

    fun setTodoItems(holder: TodoItemsHolder)
    {
        _todoItemsHolder = holder
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        TODO("Not yet implemented")
        val context = parent.context
        val view = LayoutInflater.from(context)
                .inflate(R.layout.row_todo_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//        if(_todoItemsHolder?.currentItems != null)
//            return _todoItemsHolder!!.currentItems!!.size
//        return 0
        return _todoItemsHolder?.currentItems?.size?:0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        TODO("Not yet implemented")
        val item = _todoItemsHolder?.getItem(position)?:return
        holder.text.text = item.taskText
        holder.checkBox.isChecked = item.taskIsDone

        //todo maybe implement a "edit text" fuctionality

        if (!item.taskIsDone) {
            holder.text.apply {
                paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
//            _todoItemsHolder!!.currentItems?.remove(item)
//            _todoItemsHolder!!.currentItems?.add(0, item)


        } else {
            holder.text.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
//            _todoItemsHolder!!.currentItems?.remove(item)
//            _todoItemsHolder!!.currentItems?.add(item)
        }

        holder.img.setOnClickListener{
            val callback = onTodoItemDeleteClickCallback ?: return@setOnClickListener
            callback(item)
        }
        holder.checkBox.setOnClickListener{
            val callback = onTodoItemCheckBoxClickCallback ?: return@setOnClickListener
            callback(item)
        }
    }

}