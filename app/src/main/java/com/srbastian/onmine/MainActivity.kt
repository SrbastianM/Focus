package com.srbastian.onmine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    lateinit var goToTaskActivity : Button
    lateinit var taskOnQueue : ListView

    var arrList = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToTaskActivity = findViewById(R.id.changesView)
        taskOnQueue = findViewById(R.id.listOnQueue)

        arrList = fileHelper.readData(this)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrList)
        taskOnQueue.adapter = arrayAdapter

        goToTaskActivity.setOnClickListener {
            var intent = Intent(this@MainActivity, TaskActivity:: class.java)
            startActivity(intent)
        }
        taskOnQueue.setOnItemClickListener { adapterView, view, position, l ->
            var task : String = arrList[position]
            var intent = Intent(this@MainActivity, CountdownActivity:: class.java)
            intent.putExtra("task", task)
            startActivity(intent)
        }
    }
}