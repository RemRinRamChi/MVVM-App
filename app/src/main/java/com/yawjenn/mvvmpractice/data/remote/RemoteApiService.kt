package com.yawjenn.mvvmpractice.data.remote

import com.yawjenn.mvvmpractice.data.Guess
import com.yawjenn.mvvmpractice.data.Post
import com.yawjenn.mvvmpractice.data.User
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

const val REMOTE_API_BASE_URL = "https://jsonplaceholder.typicode.com/";

interface RemoteApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): User

    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: String): List<Post>

    @POST("posts")
    suspend fun postGuess(@Body guess: Guess): Guess

    companion object{
        fun create(): RemoteApiService {

            // NOTE: Disable when HTTP logging is not required
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(REMOTE_API_BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(RemoteApiService::class.java)
        }
    }

}