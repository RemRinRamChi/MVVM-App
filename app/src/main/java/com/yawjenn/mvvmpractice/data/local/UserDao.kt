package com.yawjenn.mvvmpractice.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yawjenn.mvvmpractice.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE userId = :userId")
    suspend fun getUser(userId : String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()
}