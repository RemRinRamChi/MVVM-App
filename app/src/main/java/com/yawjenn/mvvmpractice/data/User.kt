package com.yawjenn.mvvmpractice.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yawjenn.mvvmpractice.data.local.AddressConverter

@Entity
@TypeConverters(AddressConverter::class)
data class User (
    @PrimaryKey
    var userId: String = "",
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val address: Address,
    val phone: String,
    val website: String
)