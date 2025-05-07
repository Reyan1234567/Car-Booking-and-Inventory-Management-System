package com.example.car_booking_and_inventory_management.data

data class LoginResult(
    val accessToken:String,
    val refreshToken:String,
    val user:Username
)

data class Username(
    val id: String,
    val username: String,
    val email: String,
    val phoneNumber:String,
    val profilePhoto:String,
    val licensePhoto:String,
    val firstName:String,
    val lastName:String,
    val role:String
)
