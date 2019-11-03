package com.yawjenn.mvvmpractice.data

class DataRepository {


    companion object {

        private var INSTANCE: DataRepository? = null

        @JvmStatic fun getInstance() =
            INSTANCE ?: synchronized(DataRepository::class.java) {
                INSTANCE ?: DataRepository()
                    .also { INSTANCE = it }
            }


        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}