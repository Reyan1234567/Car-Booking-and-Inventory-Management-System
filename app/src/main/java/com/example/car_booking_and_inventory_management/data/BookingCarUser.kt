package com.example.car_booking_and_inventory_management.data

data class ImageInfo(
    val _id: String = "",
    val filename: String = "",
    val path: String = "",
    val size: Int = 0,
    val url:String="",
    val uploadDate: String = "",  // ISO date as string
    val __v: Int = 0
)

data class BookingCarUser(
    val _id: String = "",
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

    val remark: String = "",

    val userImage: ImageInfo = ImageInfo(),
    val carImage: ImageInfo = ImageInfo()
)
