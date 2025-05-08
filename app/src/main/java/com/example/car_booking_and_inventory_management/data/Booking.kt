package com.example.car_booking_and_inventory_management.data

data class BookingRequest(
    val bookingStatus: String = "Pending", // "Confirmed", "Pending", "Cancelled"
    val userId: String = "",
    val carId: String = "",

    val startDate: String = "",
    val endDate: String = "",

    val pickupTime: String = "",
    val dropoffTime: String = "",

    val pickupLocationName: String = "",

    val dropoffLocationName: String = "",
    val estimatedTotalPrice: Double = 0.0,

    val remark: String = ""
)



data class BookingResponse(
    val id:String="",
    val bookingStatus: String , // "Confirmed", "Pending", "Cancelled"
    val userId: String,
    val carId: String,

    val startDate: String,
    val endDate: String,

    val pickupDate: String,
    val pickupTime: String,

    val dropoffDate: String,
    val dropoffTime: String,

    val pickupLocationName: String ,

    val dropoffLocationName: String ,
    val estimatedTotalPrice: Double?,

    val remark: String
)

data class BookingTable(
    val _id: String="",
    val bookingStatus: String = "Pending", // "Confirmed", "Pending", "Cancelled"
    val username: String = "",
    val carPlate: String = "",

    val startDate: String = "",
    val endDate: String = "",

    val pickupTime: String = "",
    val dropoffTime: String = "",

    val pickupLocationName: String = "",

    val dropoffLocationName: String = "",
    val estimatedTotalPrice: Double = 0.0,

    val remark: String = ""
)