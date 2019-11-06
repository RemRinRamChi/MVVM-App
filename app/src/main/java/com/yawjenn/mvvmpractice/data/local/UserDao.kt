package com.yawjenn.mvvmpractice.data.local

import androidx.room.*
import com.yawjenn.mvvmpractice.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun getUser(userId : String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()
}