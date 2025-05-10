package com.example.car_booking_and_inventory_management.data



data class UserPPLP(
    val _id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val LP: ImageDocument?,
    val PP: ImageDocument?,
    val birthDate: String, // Based on schema type String
)



data class ImageDocument (
    val filename: String,
    val path: String,
    val size: Number,
    val url: String
)
