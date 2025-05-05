package com.example.car_booking_and_inventory_management.data

data class accountEdit(
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profileImage: String? = null,
    val licenceImage:String?=null
){
    fun toUpdateMap(): Map<String, Any?> = mapOf(
        "name" to username,
        "email" to email,
        "phoneNumber" to phoneNumber,
        "profileImage" to profileImage,
        "licenceImage" to licenceImage
    ).filterValues { it != null }
}
