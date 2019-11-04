package com.yawjenn.mvvmpractice.home

import androidx.lifecycle.*
import com.yawjenn.mvvmpractice.Event
import com.yawjenn.mvvmpractice.data.DataCallback
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.User

const val AWAITING_INPUT: String = "Awaiting Input"
const val LOADING_USER: String = "Loading User"
const val PLEASE_ENTER_USER: String = "Please Enter a Valid User Id"

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

    fun start(){
        _message.value = _message.value?: AWAITING_INPUT
    }

    fun loadUser(){
        userId.value?.let {
            _message.value = LOADING_USER

            dataRepository.getUser(it, object : DataCallback<User>{
                override fun onDataLoaded(data: User) {
                    _userName.value = data.username
                    _email.value = data.email

                    _message.value = ""
                }

                override fun onDataNotAvailable(message: String) {
                    userId.value = ""
                    _userName.value = ""
                    _email.value = ""

                    _message.value = message
                }

            })
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