package com.yawjenn.mvvmpractice.data

import com.yawjenn.mvvmpractice.data.local.PlaceholderDatabase
import com.yawjenn.mvvmpractice.data.local.UserDao
import com.yawjenn.mvvmpractice.data.remote.JsonPlaceholderApiService
import com.yawjenn.mvvmpractice.util.log


class DataRepository(
    private val _jsonApiService: JsonPlaceholderApiService,
    private val _placeholderDatabase: PlaceholderDatabase
) {
    private var _userCache: MutableMap<String, User> = mutableMapOf()
    private val _userDao: UserDao = _placeholderDatabase.userDao()

    suspend fun getUser(userId: String) : User{

        val cached = _userCache[userId]
        if(cached != null){
            "User loaded from CACHE".log()
            return cached
        }

        val persisted = _userDao.getUser(userId)
        if(persisted != null){
            "User loaded from DATABASE".log()
            return persisted.also {
                _userCache[userId] = it
            }
        }

        val fetched = _jsonApiService.getUser(userId)
        "User loaded from WEB".log()

        return fetched.also {
            _userDao.insertUser(it.also { it.userId = userId })
            _userCache[userId] = it
        }
    }

    suspend fun deleteAllUsers() : Unit{
        _userCache = mutableMapOf()
        _userDao.deleteAllUsers()
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