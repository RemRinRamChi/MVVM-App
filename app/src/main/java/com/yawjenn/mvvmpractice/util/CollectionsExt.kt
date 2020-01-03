package com.yawjenn.mvvmpractice.util

fun <T> MutableCollection<T>.clearAndAddAll(newCollection: Collection<T>){
    clear()
    addAll(newCollection)
}