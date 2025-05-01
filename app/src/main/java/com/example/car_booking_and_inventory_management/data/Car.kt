package com.example.car_booking_and_inventory_management.data

import java.util.Date

data class Car(
    val plate:String="00000",
    val name: String = "Unknown Car",
    val make: String = "Unknown Make",
    val price: Int = 0,
    val model: String = "Unknown Model",
    val year: Int = 2000, // Reasonable default year
    val category: String = "Uncategorized",
    val type: String = "Standard",
    val transmissionType: String = "Automatic",
    val fuelType: String = "Gasoline",
    val passengerCapacity: Int = 5, // Typical default
    val luggageCapacity: String = "Not specified",
    val imageUrl: String = "", // Empty string for no image
    val dailyRate: Int = 50, // Reasonable default rate
    val hourlyRate: Int = 10,
    val notAvailableOn: List<AvailabilityPeriod> = emptyList() // Empty list instead of null
) {
    data class AvailabilityPeriod(
        val startDate: Date = Date(), // Current date as default
        val endDate: Date = Date()
    )
}