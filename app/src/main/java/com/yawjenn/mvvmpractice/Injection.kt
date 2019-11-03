package com.yawjenn.mvvmpractice

import android.content.Context
import com.yawjenn.mvvmpractice.data.DataRepository

object Injection {

    fun provideDataRepository(context: Context): DataRepository {
//        val database = ToDoDatabase.getInstance(context)
        return DataRepository.getInstance()
    }
}
