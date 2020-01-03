package com.yawjenn.mvvmpractice.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yawjenn.mvvmpractice.data.DataRepository

class WebViewViewModel(private val dataRepository: DataRepository)  : ViewModel() {
    private val _showProgress = MutableLiveData<Boolean>().apply { value = true }
    val showProgress : LiveData<Boolean>
        get() = _showProgress

    fun onPageFinishedLoading(){
        _showProgress.value = false
    }
}
