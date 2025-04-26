package com.example.car_booking_and_inventory_management.data

data class Location(
    val name: String,
    val city: String,
    val country: String = "Ethiopia",
    val type: LocationType,
    val searchTerms: List<String> = emptyList()
)

enum class LocationType {
    CITY, AIRPORT, LANDMARK
}
