package com.example.ecommercekotlin.viewmodel

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Locale
import java.util.concurrent.TimeUnit

class RandomNumberViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("TimerPrefs", Context.MODE_PRIVATE)

    private val _timeLeft = MutableLiveData<String>()
    val timeLeft: LiveData<String> get() = _timeLeft

    private val _randomNumber = MutableLiveData<Int>()
    val randomNumber: LiveData<Int> get() = _randomNumber

    private var countDownTimer: CountDownTimer? = null
    private var timerDuration = 6 * 60 * 60 * 1000L // 6 hours in milliseconds

    init {
        startTimerFromStoredTime()
        generateRandomNumber()
    }

    private fun startTimerFromStoredTime() {
        val startTime = prefs.getLong("startTime", 0L)
        if (startTime == 0L) {
            // First time starting the timer, save the start time
            saveStartTime()
            startTimer(timerDuration)
        } else {
            val elapsedTime = System.currentTimeMillis() - startTime
            val remainingTime = timerDuration - elapsedTime

            if (remainingTime > 0) {
                startTimer(remainingTime)
            } else {
                // If the timer has already finished, generate a new random number and restart the timer
                generateRandomNumber()
                saveStartTime()
                startTimer(timerDuration)
            }
        }
    }

    private fun startTimer(timeInMillis: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                generateRandomNumber()
                saveStartTime()
                startTimer(timerDuration) // Restart the timer with 6 hours
            }
        }.start()
    }

    private fun formatTime(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun generateRandomNumber() {
        _randomNumber.value = (1..190).random()
    }

    private fun saveStartTime() {
        prefs.edit().putLong("startTime", System.currentTimeMillis()).apply()
    }
}