package com.example.car_booking_and_inventory_management.data

data class saveResponse(
    val message: String, // Corresponds to "message"
    val user: User       // Corresponds to the nested "user" object
    )

    data class User(
        val id: String,         // Corresponds to "id" (assuming the ID is represented as a String)
        val username: String,   // Corresponds to "username"
        val email: String,      // Corresponds to "email"
        val phoneNumber: String,// Corresponds to "phoneNumber"
        val profilePhoto: String?, // Corresponds to "profilePhoto". Using String? because the source `pp?pp.url:updatedUser.profilePhoto` suggests it could potentially be null or missing if `pp` is null and `updatedUser.profilePhoto` is null/empty.
        val licensePhoto: String?  // Corresponds to "licensePhoto". Similar reasoning as profilePhoto.
)
