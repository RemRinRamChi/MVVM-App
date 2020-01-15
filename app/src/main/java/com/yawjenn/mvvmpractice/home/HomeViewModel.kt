package com.yawjenn.mvvmpractice.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yawjenn.mvvmpractice.Event
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.User
import com.yawjenn.mvvmpractice.util.log
import com.yawjenn.mvvmpractice.util.logError
import kotlinx.coroutines.launch

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val userId = MutableLiveData<String>()

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress : LiveData<Boolean>
        get() = _showProgress

    private val _showErr = MutableLiveData<Boolean>()
    val showErr : LiveData<Boolean>
        get() = _showErr


    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _enterUserEvent = MutableLiveData<Event<String>>()
    val enterUserEvent: LiveData<Event<String>>
        get() = _enterUserEvent

    private val _guessWordEvent = MutableLiveData<Event<Unit>>()
    val guessWordEvent: LiveData<Event<Unit>>
        get() = _guessWordEvent

    private val _showHelpEvent = MutableLiveData<Event<Unit>>()
    val showHelpEvent: LiveData<Event<Unit>>
        get() = _showHelpEvent


    fun loadUser(){
        userId.value?.let {
            _showProgress.value = true
            _showErr.value = false

            viewModelScope.launch {
                try {

                    val data: User = dataRepository.getUser(it)

                    setUserInfo(data)

                } catch (e: Exception) {

                    e.toString().logError()

                    setUserInfo(null)
                    _showErr.value = true

                } finally {
                    _showProgress.value = false
                }
            }
        }
    }

    fun enterUser(){
        _showErr.value = false

        with (userId.value){
            if(this != null && this.isNotEmpty()){
                _enterUserEvent.value = Event(this)

            } else {
                _showErr.value = true

            }
        }
    }

    fun refresh(){
        viewModelScope.launch {
            dataRepository.resetData()
            loadUser()
        }
    }

    fun guessWord(){
        _guessWordEvent.value = Event(Unit)
    }

    fun showHelp(){
        _showHelpEvent.value = Event(Unit)
    }

    private fun setUserInfo(data: User?){
        if(data != null){
            _userName.value = data.username
            _email.value = data.email
        } else {
            userId.value = ""
            _userName.value = ""
            _email.value = ""
        }
    }
}