package com.example.car_booking_and_inventory_management.data

import android.media.Image
import android.net.Uri
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
)

data class CarResponse(
    val _id:String?,
    val plate:String?,
    val name: String ?,
    val make: String?,
    val price: Int?,
    val model: String?,
    val year: Int?, // Reasonable default year
    val category: String?,
    val type: String ?,
    val transmissionType: String ?,
    val fuelType: String ?,
    val passengerCapacity: Int ?, // Typical default
    val luggageCapacity: String ?,
    val imageUrl: String ?, // Empty string for no image
    val dailyRate: Int?, // Reasonable default rate
    )

data class CarCI(
    val _id:String?,
    val plate:String?,
    val name: String ?,
    val make: String?,
    val price: Int?,
    val model: String?,
    val year: Int?, // Reasonable default year
    val category: String?,
    val type: String ?,
    val transmissionType: String ?,
    val fuelType: String ?,
    val passengerCapacity: Int ?, // Typical default
    val luggageCapacity: String ?,
    val CI: ImageDocument ?, // Empty string for no image
    val dailyRate: Int?, // Reasonable default rate
)

data class CarPost(
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
    val luggageCapacity: Int,
    val imageUrl: String="", // Empty string for no image
    val dailyRate: Int = 50, // Reasonable default rate
)


