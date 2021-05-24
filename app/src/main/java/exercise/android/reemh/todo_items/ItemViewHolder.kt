package exercise.android.reemh.todo_items

import android.media.Image
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val text: TextView = view.findViewById(R.id.todoItemText)
    val img: ImageView = view.findViewById(R.id.todoItemDeleteButton)
    val checkBox: CheckBox = view.findViewById(R.id.todoCheckBox)
}