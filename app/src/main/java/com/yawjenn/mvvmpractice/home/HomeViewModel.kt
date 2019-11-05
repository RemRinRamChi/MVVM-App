package com.yawjenn.mvvmpractice.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yawjenn.mvvmpractice.Event
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.User
import com.yawjenn.mvvmpractice.util.log
import kotlinx.coroutines.launch

const val AWAITING_INPUT: String = "Awaiting Input"
const val LOADING_USER: String = "Loading User"
const val PLEASE_ENTER_USER: String = "Please Enter a Valid User Id"
const val DATA_NULL : String = "Data was null"
const val GENERAL_ERROR_MESSAGE : String = "Something went wrong"

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val userId = MutableLiveData<String>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
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


    init {
        _message.value = AWAITING_INPUT
    }


    fun loadUser(){
        userId.value?.let {
            _message.value = LOADING_USER

            viewModelScope.launch {
                try {
                    _message.value = LOADING_USER

                    val data: User = dataRepository.getUser(it)

                    _userName.value = data.username
                    _email.value = data.email
                    _message.value = ""

                } catch (e: Exception) {

                    e.toString().log()

                    userId.value = ""
                    _userName.value = ""
                    _email.value = ""
                    _message.value = GENERAL_ERROR_MESSAGE

                }
            }
        }
    }

    fun enterUser(){
        with (userId.value){
            if(this != null && this.isNotEmpty() && _message.value.isNullOrEmpty()){
                _enterUserEvent.value = Event(this)

            } else {
                _message.value = PLEASE_ENTER_USER

            }
        }
    }
}