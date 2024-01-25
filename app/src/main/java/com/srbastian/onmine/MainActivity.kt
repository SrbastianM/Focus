package com.srbastian.onmine

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MainActivity : AppCompatActivity() {
    lateinit var progressBar : CircularProgressBar
    lateinit var startButton : Button
    var progressCount : Float = 100f
    var timer  = 25
    var timerStatus = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            if(timer <= 26) {
                Log.d("test", "prueba")
                timerStatus += 1f
                updateProgress()
            } else {
                Toast.makeText(applicationContext, "Something was wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun updateProgress(){
        progressBar.onProgressChangeListener = { progress ->
            progressBar.apply {
                progressMax = timerStatus
                setProgressWithAnimation(60f, 1000)
                progressBarWidth = 8f
                backgroundProgressBarWidth = 2f
                progressBarColor = Color.BLUE
            }
        }
    }
}