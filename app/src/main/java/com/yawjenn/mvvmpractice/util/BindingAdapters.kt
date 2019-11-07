package com.yawjenn.mvvmpractice.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

@BindingAdapter("app:disableIfEmptyOrNull")
fun disableIfEmptyOrNull(view: View, string: MutableLiveData<String>) {
    view.isEnabled = !string.value.isNullOrEmpty()
}