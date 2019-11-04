package com.yawjenn.mvvmpractice.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun TextView.bindTextData(owner: LifecycleOwner, liveData: LiveData<String>){
    liveData.observe(owner, Observer {text ->
        this.text = text
    })
}


fun EditText.bindEditTextData(owner: LifecycleOwner, liveData: MutableLiveData<String>){
    val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            liveData.value = text.toString()
        }

    }

    addTextChangedListener(watcher)

    liveData.observe(owner, Observer { text ->
        removeTextChangedListener(watcher)

        this.setText(text)
        this.setSelection(text.length)

        addTextChangedListener(watcher)
    })
}