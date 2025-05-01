package com.example.car_booking_and_inventory_management.data

data class CarFilters(
    var pickUp:String,
    var dropOff:String=pickUp,
    var startDate:String,
    var endDate:String,
    var startTime:String,
    var endTime:String
)
