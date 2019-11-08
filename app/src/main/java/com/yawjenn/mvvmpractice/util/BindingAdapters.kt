package com.yawjenn.mvvmpractice.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

@BindingAdapter("app:disableIfEmptyOrNull")
fun disableIfEmptyOrNull(view: View, string: MutableLiveData<String>) {
    view.isEnabled = !string.value.isNullOrEmpty()
}

@BindingAdapter(value = ["app:mark", "app:markedColor", "app:unmarkedColor"], requireAll = true)
fun changeColorBasedOnMark(view: View, mark: Boolean, markedColor: Int, unmarkedColor: Int) {
    view.setBackgroundColor(if (mark) markedColor else unmarkedColor)
}