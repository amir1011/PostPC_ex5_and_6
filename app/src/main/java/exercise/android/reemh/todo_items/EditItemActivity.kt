package exercise.android.reemh.todo_items

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.concurrent.TimeUnit


class EditItemActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        val itemText = findViewById<TextView>(R.id.editText)
        val inProgress = findViewById<CheckBox>(R.id.todoCheckBoxEdit)
        val creationDate = findViewById<TextView>(R.id.creationTime)
        val modDate = findViewById<TextView>(R.id.lastModification)
        val editItemIntent: Intent = intent
        val editItem: TodoItem = editItemIntent.getSerializableExtra("cur_item") as TodoItem

        itemText.text = String.format("Description: \n%s",
                editItem.taskText)
        creationDate.text = String.format("Creation date: %s",
                editItem.creationDate)
        modDate.text = String.format("Last Modified: %s",
                getLastModified(editItem.lastModified!!))
        inProgress.isChecked = editItem.taskIsDone

        inProgress.setOnClickListener {
            editItem.setState(inProgress.isChecked)
            editItem.modifyDate(Calendar.getInstance().time)
            modDate.text = String.format("Last Modified: %s",
                    getLastModified(editItem.lastModified!!))
            sendBroadcast(editItem)
        }

        itemText.setOnClickListener {
//            itemText.text
            itemText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                @RequiresApi(Build.VERSION_CODES.N)
                override fun afterTextChanged(s: Editable) {
//                editItem.setText(itemText.text.toString())
                    editItem.modifyDate(Calendar.getInstance().time)
//                modDate.text = String.format("Last Modified: %s",
//                        getLastModified(modDate.text as Date))
                    sendBroadcast(editItem)
                }
            })

//            editItem.modifyDate(Calendar.getInstance().time)
////                modDate.text = String.format("Last Modified: %s",
////                        getLastModified(modDate.text as Date))
//            sendBroadcast(editItem)
        }

//        itemText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//            @RequiresApi(Build.VERSION_CODES.N)
//            override fun afterTextChanged(s: Editable) {
////                editItem.setText(itemText.text.toString())
//                editItem.modifyDate(Calendar.getInstance().time)
////                modDate.text = String.format("Last Modified: %s",
////                        getLastModified(modDate.text as Date))
//                sendBroadcast(editItem)
//            }
//        })

    }

    private fun getLastModified(lastModified: Date): String {
        val curDate: Date = Calendar.getInstance().time
        val diff: Long = curDate.time - lastModified.time
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours: Long = TimeUnit.MILLISECONDS.toHours(diff)
        if (minutes < 60) {
            return minutes.toString() + " minutes ago"
        }
        return if (hours < 24) {
            "Today at $hours"
        } else lastModified.toString() + " at " + hours % 24
    }

    private fun sendBroadcast(item: TodoItem)
    {
        val updatedIntent = Intent("edit_item")
        updatedIntent.action = "edit_item"
        updatedIntent.putExtra("edited_item", item)
        sendBroadcast(updatedIntent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}