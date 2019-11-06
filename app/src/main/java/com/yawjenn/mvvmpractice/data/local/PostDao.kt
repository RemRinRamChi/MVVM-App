package com.yawjenn.mvvmpractice.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yawjenn.mvvmpractice.data.Post

@Dao
interface PostDao {
    @Query("SELECT COUNT(*) FROM Post WHERE user_id = :userId")
    suspend fun getPostCount(userId : String): Int

    @Query("SELECT * FROM Post WHERE user_id = :userId ORDER BY id ASC")
    fun getPosts(userId : String): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePost(post: Post)

    @Query("DELETE FROM Post")
    suspend fun deleteAllPosts()
}