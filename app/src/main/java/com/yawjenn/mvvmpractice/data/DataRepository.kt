package com.yawjenn.mvvmpractice.data

import androidx.lifecycle.LiveData
import com.yawjenn.mvvmpractice.data.local.PlaceholderDatabase
import com.yawjenn.mvvmpractice.data.local.PostDao
import com.yawjenn.mvvmpractice.data.local.UserDao
import com.yawjenn.mvvmpractice.data.remote.JsonPlaceholderApiService
import com.yawjenn.mvvmpractice.util.log


class DataRepository(
    private val _jsonApiService: JsonPlaceholderApiService,
    private val _placeholderDatabase: PlaceholderDatabase
) {
    private var _userCache: MutableMap<String, User> = mutableMapOf()
    private val _userDao: UserDao = _placeholderDatabase.userDao()

    private val _postDao: PostDao = _placeholderDatabase.postDao()

    private var _guessCache: MutableMap<String, Guess> = mutableMapOf()

    suspend fun getUser(userId: String) : User{

        val cached = _userCache[userId]
        if(cached != null){
            "User loaded from CACHE: $cached".log()
            return cached
        }

        val persisted = _userDao.getUser(userId)
        if(persisted != null){
            "User loaded from DATABASE: $persisted".log()
            return persisted.also {
                _userCache[userId] = it
            }
        }

        val fetched = _jsonApiService.getUser(userId)
        "User loaded from WEB: $fetched".log()

        return fetched.also {
            _userDao.insertUser(it)
            _userCache[userId] = it
        }
    }

    suspend fun getPosts(userId: String) : LiveData<List<Post>>{

        _userDao.getUser(userId) ?: getUser(userId)

        val postCountInDb = _postDao.getPostCount(userId)
        if(postCountInDb > 0){
            "DATABASE contains $postCountInDb posts".log()
        } else {
            val fetched = _jsonApiService.getPosts(userId)
            "${fetched.size} Posts loaded from WEB".log()

            _postDao.insertPosts(fetched)
        }

        return _postDao.getPosts(userId)
    }

    suspend fun updatePost(post: Post){
        _postDao.updatePost(post)
    }

    suspend fun submitGuess(guessWord: String) : Guess {
        val cached = _guessCache[guessWord]
        if(cached != null){
            return cached
        }

        return _jsonApiService.postGuess(Guess.generateRandomGuess(guessWord)).also {
            _guessCache[guessWord] = it
        }
    }

    suspend fun resetData() {
        _userCache = mutableMapOf()
        _userDao.deleteAllUsers()
        _postDao.deleteAllPosts()
    }


    companion object {

        private var INSTANCE: DataRepository? = null

        @JvmStatic fun getInstance(jsonPlaceholderApiService: JsonPlaceholderApiService, placeholderDatabase: PlaceholderDatabase) =
            INSTANCE ?: synchronized(DataRepository::class.java) {
                INSTANCE ?: DataRepository(jsonPlaceholderApiService, placeholderDatabase)
                    .also { INSTANCE = it }
            }


        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}