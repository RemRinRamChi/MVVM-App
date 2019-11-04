package com.yawjenn.mvvmpractice.data

const val DATA_NULL : String = "Data was null"
const val GENERAL_ERROR_MESSAGE : String = "Something went wrong"

interface DataCallback<T> {
    fun onDataLoaded(data: T)

    fun onDataNotAvailable(message: String = GENERAL_ERROR_MESSAGE)
}