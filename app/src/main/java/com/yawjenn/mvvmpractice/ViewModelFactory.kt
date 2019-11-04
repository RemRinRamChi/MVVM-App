package com.yawjenn.mvvmpractice

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.home.HomeViewModel
import com.yawjenn.mvvmpractice.tasks.TasksViewModel

class ViewModelFactory private constructor(
        private val dataRepository: DataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(HomeViewModel::class.java) ->
                        HomeViewModel(dataRepository)
                    isAssignableFrom(TasksViewModel::class.java) ->
                        TasksViewModel(dataRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(activity: Activity?) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(
                            Injection.provideDataRepository(activity!!.application.applicationContext))
                            .also { INSTANCE = it }
                }


        @VisibleForTesting fun destroyInstance() {
            INSTANCE = null
        }
    }
}
