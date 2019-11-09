package com.yawjenn.mvvmpractice.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

/**
 * Makes a view INVISIBLE if supplied LiveData String is null or empty
 */
@BindingAdapter("app:hideIfEmptyOrNull")
fun hideIfEmptyOrNull(view: View, string: MutableLiveData<String>) {
    view.visibility = if(string.value.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
}

/**
 * Disables a view if supplied LiveData String is null or empty
 */
@BindingAdapter("app:disableIfEmptyOrNull")
fun disableIfEmptyOrNull(view: View, string: MutableLiveData<String>) {
    view.isEnabled = !string.value.isNullOrEmpty()
}

/**
 * Changes view's backgroundColor when view is marked/unmarked
 */
@BindingAdapter(value = ["app:mark", "app:markedColor", "app:unmarkedColor"], requireAll = true)
fun changeColorBasedOnMark(view: View, mark: Boolean, markedColor: Int, unmarkedColor: Int) {
    view.setBackgroundColor(if (mark) markedColor else unmarkedColor)
}