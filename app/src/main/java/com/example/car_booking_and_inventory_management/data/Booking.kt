package com.example.car_booking_and_inventory_management.data

data class Booking(
    val bookingId: String = "",
    val bookingTimestamp: String = "", // ISO 8601 string or parse to Date if needed
    val bookingStatus: String = "Pending", // "Confirmed", "Pending", "Cancelled"

    val userId: String = "",
    val carId: String = "",

    val startDate: String = "",
    val endDate: String = "",

    val pickupDate: String = "",
    val pickupTime: String = "",

    val dropoffDate: String = "",
    val dropoffTime: String = "",

    val pickupLocationId: String = "",
    val pickupLocationName: String = "",

    val dropoffLocationId: String = "",
    val dropoffLocationName: String = "",


    val additionalFees: AdditionalFees = AdditionalFees(),

    val estimatedTotalPrice: Double = 0.0,

    val paymentStatus: String = "Pending", // "Paid", "Unpaid", "Pending"

    val optionalExtras: OptionalExtras = OptionalExtras(),

    val remark: String = ""
)

data class AdditionalFees(
    val airportSurcharge: Double = 0.0
)

data class OptionalExtras(
    val childSeat: Int = 0,
    val gpsNavigation: Boolean = false
)

