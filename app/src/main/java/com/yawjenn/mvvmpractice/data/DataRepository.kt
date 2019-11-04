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

    fun getUser(userId: String, callback: DataCallback<User>) {

        val cached = _userCache[userId]
        if(cached != null){
            "User loaded from CACHE".log()

            callback.onDataLoaded(cached)
            return
        }

        jsonApiService.getUser(userId).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body().let {
                    "User loaded from WEB".log()

                    if(it != null){
                        callback.onDataLoaded(it)
                        _userCache.put(userId, it)
                    } else {
                        callback.onDataNotAvailable(DATA_NULL)
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onDataNotAvailable(t.message ?: GENERAL_ERROR_MESSAGE)
            }

        })
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