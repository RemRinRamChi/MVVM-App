package com.yawjenn.mvvmpractice.data.remote

import com.yawjenn.mvvmpractice.data.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApiService {
    @GET("users/{id}")
    fun getUser(@Path("id") userId: String): Call<User>

    companion object{
        fun create(): JsonPlaceholderApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build()

            return retrofit.create(JsonPlaceholderApiService::class.java)
        }
    }

}