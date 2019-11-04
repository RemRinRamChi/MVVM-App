package com.yawjenn.mvvmpractice.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yawjenn.mvvmpractice.data.DataRepository

class TasksViewModel(private val dataRepository: DataRepository)  : ViewModel() {
    private val _userId = MutableLiveData<String>()
    val userId : LiveData<String>
        get() = _userId

    fun loadUser(userId: String?){
        _userId.value = userId
    }
}
