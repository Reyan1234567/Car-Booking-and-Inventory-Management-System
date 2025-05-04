package com.example.car_booking_and_inventory_management.data

data class ProfilePageRequest(
    val username:String,
    val email:String,
    val profilePhoto:String,
    val licensePhoto:String,
    val phoneNumber:String
)

data class ProfilePageResult(
    val id:String,
    val username:String,
    val email:String,
    val profilePhoto:String,
    val licensePhoto:String,
    val phoneNumber:String
)
