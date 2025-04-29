package com.example.car_booking_and_inventory_management.repositories

import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.data.Booking
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarFilters
import com.example.car_booking_and_inventory_management.data.Location
import com.example.car_booking_and_inventory_management.network.searchApi
import retrofit2.Response

class CarFilterRepository(private val api:searchApi, private val tokenManager: TokenManager){
    suspend fun getCars(): Response<List<Car>>{
       return api.getAllCars()
    }

    suspend fun getFilteredCars(body:CarFilters): Response<List<Car>> {
        return api.getFilteredCars(body)
    }

    suspend fun getLocations(query:String):Response<List<Location>>{
        return api.getLocations(query)
    }

//    suspend fun getUsername():String?{
//        return tokenManager.getUsername()
//    }

    suspend fun createBooking(booking:Booking): Response<Booking>{
        return api.createBooking(booking)
    }

    suspend fun checkLegitimacy():Boolean{
        val license=tokenManager.getLicensePhoto()
        val profile=tokenManager.getProfilePhoto()

        if(license==null||profile==null){
            return false
        }
        else if(license=="Not found"||profile=="Not found"){
            return false
        }
        else{
            return true
        }
    }
}