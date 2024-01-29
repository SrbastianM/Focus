package com.srbastian.onmine

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class TaskActivity : AppCompatActivity() {
    // tasks variables
    lateinit var itemTask : ListView
    lateinit var saveTask : Button
    lateinit var nameTask: EditText

    var arrList = ArrayList<String>()
    var fileHelper = FileHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_layout)
        Log.d("Test", "test")

        saveTask = findViewById(R.id.saveToDoButton)
        itemTask = findViewById(R.id.taskList)
        nameTask= findViewById(R.id.toDoTasks)

        // We check if already have a task in the memory
        arrList = fileHelper.readData(this)
        // now we need to assign the item for a list view,
        // Well we use an adapter for this
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrList)
        itemTask.adapter = arrayAdapter

        saveTask.setOnClickListener {
            val itemName : String = nameTask.text.toString()
            arrList.add(itemName)
            nameTask.setText("")
            fileHelper.writeData(arrList, applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        itemTask.setOnItemClickListener { adapterView, view, position, l ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("is Completed?")
            alert.setMessage("Do you want to Delete this task?")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                arrList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(arrList, applicationContext)
            })
            alert.create().show()
        }

    }
}