package com.yawjenn.mvvmpractice.guess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.Guess
import com.yawjenn.mvvmpractice.util.logError
import kotlinx.coroutines.launch

class GuessViewModel(private val dataRepository: DataRepository)   : ViewModel() {
    val guessWord = MutableLiveData<String>().apply { value = "" }

    private val _guess = MutableLiveData<String>()
    val guess : LiveData<String>
        get() = _guess

    fun submitGuessWord(){
        guessWord.value?.let {
            viewModelScope.launch {
                try {
                    _guess.value = "loading . . . . ."

                    val guess: Guess = dataRepository.submitGuess(it)
                    _guess.value = guess.guess

                } catch (e: Exception) {
                    _guess.value = "an error occurred"

                    e.toString().logError()
                }
            }
        }
    }



}
