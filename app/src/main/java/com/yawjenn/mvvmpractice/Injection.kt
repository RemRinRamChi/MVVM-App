package com.yawjenn.mvvmpractice

import android.content.Context
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.local.PlaceholderDatabase
import com.yawjenn.mvvmpractice.data.remote.JsonPlaceholderApiService

object Injection {

    fun provideDataRepository(context: Context): DataRepository {
        val database = PlaceholderDatabase.getInstance(context)
        return DataRepository.getInstance(JsonPlaceholderApiService.create(), database)
    }
}
