package exercise.android.reemh.todo_items

import java.io.Serializable

class TodoItem : Serializable { // TODO: edit this class as you want
    var taskIsDone: Boolean = false
    var taskText: String = ""

    fun setText(newText: String?)
    {
        if(newText == null)
            return
        taskText = newText
    }



    fun setState(setState : Boolean)
    {
        taskIsDone = setState
    }
}