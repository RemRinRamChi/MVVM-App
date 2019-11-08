package com.yawjenn.mvvmpractice.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User (
    @PrimaryKey
    @ColumnInfo(index = true)
    var id: String = "",
    val name: String = "",
    @ColumnInfo(name = "user_name")
    val username: String = "",
    val email: String = "",
    @Embedded
    val address: Address,
    val phone: String,
    val website: String
)

data class Address (
    val street: String = "",
    val suite: String = "",
    val city: String = "",
    @field:Json(name = "zipcode")
    @ColumnInfo(name = "zip_code")
    val zipCode : String = ""
)