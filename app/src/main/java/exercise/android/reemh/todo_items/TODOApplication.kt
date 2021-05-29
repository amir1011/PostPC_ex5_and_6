package exercise.android.reemh.todo_items

import android.app.Application

class TODOApplication: Application() {

    private var currItems: TodoItemsHolderImpl? = null
    private var currAdapter: ItemAdapter? = null

    companion object {
        private var instance: TODOApplication? = null
        fun getInstance(): TODOApplication? {
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        currItems = TodoItemsHolderImpl(this)
        currAdapter = ItemAdapter()
        currAdapter!!.setTodoItems(currItems!!)
    }

    fun getHolder(): TodoItemsHolderImpl? {
        return currItems
    }
    fun getAdapter(): ItemAdapter? {
        return currAdapter
    }

}