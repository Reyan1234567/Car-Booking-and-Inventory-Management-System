package com.example.car_booking_and_inventory_management.data

data class ImageInfo(
    val _id: String?,
    val filename: String?,
    val path: String?,
    val size: Int?,
    val url:String?,
    val uploadDate: String?,  // ISO date as string
)

data class BookingCarUser(
    val _id: String,
    val bookingStatus: String? , // "Confirmed", "Pending", "Cancelled"
    val username: String?,
    val carPlate: String?,

    val startDate: String?,
    val endDate: String? ,

    val pickupTime: String? ,
    val dropoffTime: String? ,

    val pickupLocationName: String?,
    val dropoffLocationName: String? ,
    val estimatedTotalPrice: Double?,

    val remark: String?,

    val profilePhotos: ImageInfo?,
    val carImages: ImageInfo?
)
