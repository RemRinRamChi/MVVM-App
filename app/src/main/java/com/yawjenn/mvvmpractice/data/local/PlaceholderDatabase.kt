package com.yawjenn.mvvmpractice.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yawjenn.mvvmpractice.data.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class PlaceholderDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: PlaceholderDatabase? = null

        fun getInstance(context: Context): PlaceholderDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaceholderDatabase::class.java,
                    "placeholder_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}