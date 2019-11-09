package com.yawjenn.mvvmpractice.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yawjenn.mvvmpractice.data.Post
import com.yawjenn.mvvmpractice.data.User

const val LOCAL_DATABASE_NAME = "local_database"

@Database(entities = [User::class, Post::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    LOCAL_DATABASE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}