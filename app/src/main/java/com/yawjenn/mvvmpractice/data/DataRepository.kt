package com.yawjenn.mvvmpractice.data

import com.yawjenn.mvvmpractice.data.remote.JsonPlaceholderApiService
import com.yawjenn.mvvmpractice.util.log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataRepository(
    private val jsonApiService: JsonPlaceholderApiService
) {
    private val _userCache: MutableMap<String, User> = mutableMapOf()

    suspend fun getUser(userId: String) : User{

        val cached = _userCache[userId]
        if(cached != null){
            "User loaded from CACHE".log()
            return cached
        }

        return jsonApiService.getUser(userId).also {
            "User loaded from WEB".log()
            _userCache[userId] = it
        }
    }


    companion object {

        private var INSTANCE: DataRepository? = null

        @JvmStatic fun getInstance(jsonPlaceholderApiService: JsonPlaceholderApiService) =
            INSTANCE ?: synchronized(DataRepository::class.java) {
                INSTANCE ?: DataRepository(jsonPlaceholderApiService)
                    .also { INSTANCE = it }
            }


        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}