package com.yawjenn.mvvmpractice.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.ViewModelFactory


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(activity)).get(viewModelClass)

fun Fragment.getFragmentTag(): String{
    return this.javaClass.simpleName
}

fun Fragment.replaceFragment(newFragment: Fragment){
    activity?.supportFragmentManager?.commit {
        replace(R.id.container, newFragment, newFragment.getFragmentTag())
        addToBackStack(newFragment.getFragmentTag())
    }
}