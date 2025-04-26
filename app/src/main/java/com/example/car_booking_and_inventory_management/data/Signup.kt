package com.example.car_booking_and_inventory_management.data

import android.provider.ContactsContract
import java.util.Date

data class Signup(
    val firstname:String,
    val lastname:String,
    val phoneNumber: String,
    val birthDate: Date,
    val email: String,
    val username:String,
    val password:String,
)

