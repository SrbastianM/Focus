package com.srbastian.onmine

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.text.DecimalFormat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var progressCircularBar : CircularProgressBar
    lateinit var timeLeftText : TextView
    lateinit var startButton : ImageButton
    lateinit var stopButton : ImageButton
    lateinit var customCountDownTimer : CustomCountdownTimer

    private val countDownTime = 60 //seconds
    private val clockTimer = (countDownTime * 1000).toLong()
    private val progressTime = (clockTimer / 1000).toFloat()

    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        progressCircularBar = findViewById(R.id.progress_bar)
        timeLeftText = findViewById(R.id.timeLeft)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)

        var secondsLeft = 0
        customCountDownTimer = object : CustomCountdownTimer(clockTimer, 1000) {}

        customCountDownTimer.onFinish = {
            timerFormat(0, timeLeftText)
        }
        // set the circular bar progress
        progressCircularBar.progressMax = progressTime


        startButton.setOnClickListener {
            stopButton.visibility = View.VISIBLE
            customCountDownTimer.onTick = {millisUntilFinished ->
                val second = (millisUntilFinished / 1000.0f).roundToInt()
                if ( secondsLeft != second) {
                    secondsLeft = second
                    timerFormat(secondsLeft, timeLeftText)
                }
            }
            customCountDownTimer.startTimer()
        }

        stopButton.setOnClickListener {
            customCountDownTimer.pauseTimer()
//            startButton.setText(R.string.restart_button)
        }

    }

    private fun timerFormat(secondsLeft: Int, timeTxt: TextView) {
        progressCircularBar.setProgressWithAnimation(secondsLeft.toFloat(), 1000)
        progressCircularBar.progressBarColorStart  = Color.BLACK
        progressCircularBar.progressBarColorEnd = Color.GRAY
        progressCircularBar.progressBarColorDirection = CircularProgressBar.GradientDirection.LEFT_TO_RIGHT
        val decimalFormat = DecimalFormat("00")
        val hour = secondsLeft / 3600
        val min = (secondsLeft % 3600) / 60
        val seconds = secondsLeft % 60

        val timeFormatOne = decimalFormat.format(secondsLeft)
        val timeFormatTwo = decimalFormat.format(min) + ":" + decimalFormat.format(seconds)
        val timeFormatThree = decimalFormat.format(hour) + ":" + decimalFormat.format(min) + ":" + decimalFormat.format(seconds)

        timeLeftText.text = timeFormatThree
    }

    private fun onBackPressedMethod() {
        customCountDownTimer.destroyTimer()
        finish()
    }

    override fun onPause() {
        customCountDownTimer.pauseTimer()
        super.onPause()
    }

    override fun onResume() {
        customCountDownTimer.resumeTimer()
        super.onResume()
    }

    override fun onDestroy() {
        customCountDownTimer.destroyTimer()
        super.onDestroy()
    }
}