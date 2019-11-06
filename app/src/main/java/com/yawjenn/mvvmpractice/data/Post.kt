package com.yawjenn.mvvmpractice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = User::class,
    onDelete = ForeignKey.CASCADE,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("user_id"))]
)
data class Post(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId : String,
    val title: String,
    val body: String,
    var read: Boolean = false
)