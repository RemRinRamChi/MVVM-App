package com.yawjenn.mvvmpractice.data.remote

import com.yawjenn.mvvmpractice.data.Guess
import com.yawjenn.mvvmpractice.data.Post
import com.yawjenn.mvvmpractice.data.User
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface JsonPlaceholderApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): User

    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: String): List<Post>

    @POST("posts")
    suspend fun postGuess(@Body guess: Guess): Guess

    companion object{
        fun create(): JsonPlaceholderApiService {

            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(JsonPlaceholderApiService::class.java)
        }
    }

}