package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        //constants for timer
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME_TOTAL = 10000L
    }

    private val timer: CountDownTimer

    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
    get() = _remainingTime

    val remainingTimeString = Transformations.map(remainingTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        Log.i("GameViewModel", "GameViewModel created!!")
        _eventGameFinish.value = false
        resetList()
        nextWord()
        _score.value = 0 //set initial value

        timer = object : CountDownTimer(COUNTDOWN_TIME_TOTAL, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingTime.value = (millisUntilFinished / ONE_SECOND) //convert to minutes
                Log.d("GameViewModel", "timer. remain: ${millisUntilFinished/ ONE_SECOND}")
            }

            override fun onFinish() {
                _remainingTime.value = DONE
                _eventGameFinish.value = true
                Log.d("GameViewModel", "timer finished.")
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!!")
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0) //next value for display
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

}