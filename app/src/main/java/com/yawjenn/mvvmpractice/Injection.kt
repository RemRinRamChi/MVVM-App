package com.yawjenn.mvvmpractice

import android.content.Context
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.local.LocalDatabase
import com.yawjenn.mvvmpractice.data.remote.RemoteApiService

/**
 * Used to obtain objects such as the Repository for injection
 */
object Injection {

    fun provideDataRepository(context: Context): DataRepository {
        val database = LocalDatabase.getInstance(context)
        val remoteService = RemoteApiService.create()

        return DataRepository.getInstance(remoteService, database)
    }
}
