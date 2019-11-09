package com.yawjenn.mvvmpractice.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.ViewModelFactory


/**
 * Obtain a ViewModel scoped to the Fragment
 */
fun <T : ViewModel> Fragment.obtainFragmentViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(activity)).get(viewModelClass)

fun Fragment.getFragmentTag(): String{
    return this.javaClass.simpleName
}

/**
 * Replaces fragment and adds the transaction to the back stack
 */
fun Fragment.replaceFragment(newFragment: Fragment){
    activity?.supportFragmentManager?.commit {
        replace(R.id.container, newFragment, newFragment.getFragmentTag())
        addToBackStack(newFragment.getFragmentTag())
    }
}