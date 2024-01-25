package com.srbastian.onmine

import android.os.CountDownTimer

open class CustomCountdownTimer(
    private val millisInfuture : Long,
    private val countDownInterval : Long
    )
{
    private var millisUntilFinished = millisInfuture
    private var timer = InternalTimer(this, millisInfuture, countDownInterval)
    private var isRunning = false
    var onTick : ((millisUntilFinished:Long) -> Unit) ? = null
    var onFinish : (() -> Unit) ? = null


    private class InternalTimer(
        private val parent : CustomCountdownTimer,
        millisInFuture : Long,
        countDownInterval: Long
    ): CountDownTimer(millisInFuture, countDownInterval) {
        var millisUntilFinished = parent.millisUntilFinished
        override fun onTick(millisUntilFinished: Long) {
            parent.onTick?.invoke(millisUntilFinished)
        }

        override fun onFinish() {
            millisUntilFinished = 0
            parent.onFinish?.invoke()
        }

    }
    fun pauseTimer(){
        timer.cancel()
        isRunning = false
    }
    fun resumeTimer(){
        if (!isRunning && timer.millisUntilFinished > 0 ) {
            timer = InternalTimer(this, timer.millisUntilFinished, countDownInterval)
            startTimer()
        }
    }
    fun startTimer(){
        timer.start()
        isRunning = true
    }
    fun restartTimer(){
        timer.cancel()
        timer = InternalTimer(this, timer.millisUntilFinished, countDownInterval)
        startTimer()
    }
    fun destroyTimer(){
        timer.cancel()
    }
}