package com.yawjenn.mvvmpractice.data.remote

import com.yawjenn.mvvmpractice.data.Guess
import com.yawjenn.mvvmpractice.data.Post
import com.yawjenn.mvvmpractice.data.User
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface JsonPlaceholderApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): User

    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: String): List<Post>

    @POST("posts")
    suspend fun postGuess(@Body guess: Guess): Guess

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