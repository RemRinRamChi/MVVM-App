package com.yawjenn.mvvmpractice.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Address (
    val street: String = "",
    val suite: String = "",
    val city: String = "",
    @ColumnInfo(name = "zip_code")
    val zipcode : String = ""
)

@Entity
data class User (
    @PrimaryKey
    @ColumnInfo(name = "id", index = true)
    var userId: String = "",
    val name: String = "",
    @ColumnInfo(name = "user_name")
    val username: String = "",
    val email: String = "",
    @Embedded
    val address: Address,
    val phone: String,
    val website: String
)