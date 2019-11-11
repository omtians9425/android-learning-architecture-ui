package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ScoreViewModelFactory(private val finalScore: Int): ViewModelProvider.Factory {
    //instantiation method of factory pattern
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T //This method is not reified and inline, so the type information is lost.
        }
        throw  IllegalArgumentException("Unknown ViewModel Class.")
    }
}