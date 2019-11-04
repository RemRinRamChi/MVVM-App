package com.yawjenn.mvvmpractice.home

import androidx.lifecycle.*
import com.yawjenn.mvvmpractice.data.DataCallback
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.User

const val AWAITING_INPUT: String = "Awaiting Input"
const val LOADING_USER: String = "Loading User"

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val userId = MutableLiveData<String>()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    fun start(){
        _status.value = AWAITING_INPUT
    }

    fun loadUser(){
        userId.value?.let {
            _status.value = LOADING_USER

            dataRepository.getUser(it, object : DataCallback<User>{
                override fun onDataLoaded(data: User) {
                    _userName.value = data.username
                    _email.value = data.email

                    _status.value = ""
                }

                override fun onDataNotAvailable(message: String) {
                    _status.value = message
                }

            })
        }
    }
}