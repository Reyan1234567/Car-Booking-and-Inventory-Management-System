package com.example.car_booking_and_inventory_management.data

import android.provider.ContactsContract
import java.util.Date

data class Signup(
    val firstname:String,
    val lastname:String,
    val phoneNumber: String,
    val birthDate: String,
    val email: String,
    val username:String,
    val password:String,
    val rePassword:String
)

