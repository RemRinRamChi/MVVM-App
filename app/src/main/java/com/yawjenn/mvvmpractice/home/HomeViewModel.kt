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

    private val _message = MutableLiveData<Int>().apply { value = R.string.awaiting_input }
    val message: LiveData<Int>
        get() = _message

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


    fun loadUser(){
        userId.value?.let {
            _message.value = R.string.loading

            viewModelScope.launch {
                try {

                    val data: User = dataRepository.getUser(it)

                    setUserInfo(data)
                    _message.value = R.string.blank

                } catch (e: Exception) {

                    e.toString().logError()

                    setUserInfo(null)
                    _message.value = R.string.general_err_message
                }
            }
        }
    }

    fun enterUser(){
        with (userId.value){
            if(this != null && this.isNotEmpty()){
                _enterUserEvent.value = Event(this)

            } else {
                _message.value = R.string.enter_valid_user_id

            }
        }
    }

    fun refresh(){
        _message.value = R.string.refreshing_user

        viewModelScope.launch {
            dataRepository.resetData()
            loadUser()
        }
    }

    fun guessWord(){
        _guessWordEvent.value = Event(Unit)
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