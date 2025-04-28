package com.example.car_booking_and_inventory_management.data

data class LoginResult(
    val accessToken:String,
    val refreshToken:String,
    val user:Username
)

data class Username(
    val _id:String,
    val username:String,
    val email:String
)
