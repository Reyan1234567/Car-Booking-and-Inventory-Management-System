package com.example.car_booking_and_inventory_management.data



data class UserPPLP(
    val _id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val LP: String?,
    val PP: String?,
    val birthDate: String, // Based on schema type String
)

