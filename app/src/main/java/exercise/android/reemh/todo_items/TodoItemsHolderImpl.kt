package exercise.android.reemh.todo_items

import java.io.Serializable

// TODO: implement!
class TodoItemsHolderImpl : TodoItemsHolder {
    override var currentItems: MutableList<TodoItem>? = null

    override fun addNewInProgressItem(description: String?)
    {
        val todo: TodoItem = TodoItem()
        todo.setText(description)
        if(currentItems == null)
            currentItems = mutableListOf<TodoItem>(todo)
        else
            currentItems!!.add(0, todo)
    }
    override fun markItemDone(item: TodoItem?) {
        if (item == null)
            return
        item.setState(true)
    }
    override fun markItemInProgress(item: TodoItem?) {
        if (item == null)
            return
        item.setState(false)
    }
    override fun deleteItem(item: TodoItem?) {
        if(item == null || currentItems == null)
           return
        currentItems!!.remove(item)
    }

    override fun getItem(index: Int): TodoItem? {
        return currentItems?.getOrNull(index)
    }
}