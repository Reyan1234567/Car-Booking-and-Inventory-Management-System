package com.example.car_booking_and_inventory_management.data

data class accountEdit(
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profilePhoto: String? = null,
    val licensePhoto:String?=null
){
    fun toUpdateMap(): Map<String, Any?> = mapOf(
        "name" to username,
        "email" to email,
        "phoneNumber" to phoneNumber,
        "profilePhoto" to profilePhoto,
        "licensePhoto" to licensePhoto
    ).filterValues { it != null }
}

data class UsersTable(
        var _id:String,
        var username:String,
        var email:String,
        var firstname:String,
        var lastname:String,
        var phoneNumber:String,
)