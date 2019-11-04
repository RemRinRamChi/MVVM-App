package com.yawjenn.mvvmpractice.data

data class User (
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val address: Address,
    val phone: String,
    val website: String
)