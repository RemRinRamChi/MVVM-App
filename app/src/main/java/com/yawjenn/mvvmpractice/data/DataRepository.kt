package com.yawjenn.mvvmpractice.data

import androidx.lifecycle.LiveData
import com.yawjenn.mvvmpractice.data.local.LocalDatabase
import com.yawjenn.mvvmpractice.data.local.PostDao
import com.yawjenn.mvvmpractice.data.local.UserDao
import com.yawjenn.mvvmpractice.data.remote.RemoteApiService
import com.yawjenn.mvvmpractice.util.log


class DataRepository(
    private val remoteApiService: RemoteApiService,
    private val localDatabase: LocalDatabase
) {
    // User
    private var userCache: MutableMap<String, User> = mutableMapOf()
    private val userDao: UserDao = localDatabase.userDao()

    // Post
    private val postDao: PostDao = localDatabase.postDao()

    // Guess
    private var guessCache: MutableMap<String, Guess> = mutableMapOf()

    suspend fun getUser(userId: String) : User{

        val cached = userCache[userId]
        if(cached != null){
            "User loaded from CACHE: $cached".log()
            return cached
        }

        val persisted = userDao.getUser(userId)
        if(persisted != null){
            "User loaded from DATABASE: $persisted".log()
            return persisted.also {
                userCache[userId] = it
            }
        }

        val fetched = remoteApiService.getUser(userId)
        "User loaded from WEB: $fetched".log()

        return fetched.also {
            userCache[userId] = it
            userDao.insertUser(it)
        }
    }

    suspend fun getPosts(userId: String) : LiveData<List<Post>>{

        userDao.getUser(userId) ?: getUser(userId)

        val postCountInDb = postDao.getPostCount(userId)
        if(postCountInDb > 0){
            "DATABASE contains $postCountInDb posts".log()
        } else {
            val fetched = remoteApiService.getPosts(userId)
            "${fetched.size} posts loaded from WEB".log()

            postDao.insertPosts(fetched)
        }

        return postDao.getPosts(userId)
    }

    suspend fun updatePost(post: Post){
        postDao.updatePost(post)
    }

    suspend fun submitGuess(guessWord: String) : Guess {
        val cached = guessCache[guessWord]
        if(cached != null){
            return cached
        }

        return remoteApiService.postGuess(Guess.generateRandomGuess(guessWord)).also {
            guessCache[guessWord] = it
        }
    }

    suspend fun resetData() {
        userCache = mutableMapOf()
        userDao.deleteAllUsers()

        postDao.deleteAllPosts()

        guessCache = mutableMapOf()
    }


    companion object {

        private var INSTANCE: DataRepository? = null

        @JvmStatic fun getInstance(remoteApiService: RemoteApiService, localDatabase: LocalDatabase) =
            INSTANCE ?: synchronized(DataRepository::class.java) {
                INSTANCE ?: DataRepository(remoteApiService, localDatabase)
                    .also { INSTANCE = it }
            }


        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}