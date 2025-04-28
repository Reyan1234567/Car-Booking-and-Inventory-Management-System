package com.example.car_booking_and_inventory_management.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class FilterItem(
val name: String,
var value: MutableState<Boolean> = mutableStateOf(false)
)
