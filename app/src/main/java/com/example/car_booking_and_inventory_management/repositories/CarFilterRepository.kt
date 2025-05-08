package com.example.car_booking_and_inventory_management.repositories

import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.data.BookingRequest
import com.example.car_booking_and_inventory_management.data.BookingResponse
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarFilters
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.data.Location
import com.example.car_booking_and_inventory_management.data.Username
import com.example.car_booking_and_inventory_management.network.searchApi
import retrofit2.Response

class CarFilterRepository(private val api:searchApi, private val tokenManager: TokenManager){
    suspend fun getuserId():String?{
        return tokenManager.getuserId()
    }

    suspend fun getCars(): Response<List<Car>>{
       return api.getAllCars()
    }

    suspend fun getFilteredCars(body:CarFilters): Response<List<CarResponse>> {
        return api.getFilteredCars(body)
    }

    suspend fun getLocations(query:String):Response<List<Location>>{
        return api.getLocations(query)
    }

    suspend fun getUsername():String?{
        return tokenManager.getUsername()
    }

    suspend fun checkLegitimacy(username: String): Response<Username> {
        return api.checkLegitimacy(username)
    }

    suspend fun createBooking(booking: BookingRequest): Response<BookingResponse>{
        return api.createBooking(booking)
    }

    suspend fun getHistory(id:String):Response<List<BookingResponse>>{
        return api.getHistory(id)
    }
//    suspend fun checkLegitimacy():Boolean{
//        val license=tokenManager.getLicensePhoto()
//        val profile=tokenManager.getProfilePhoto()
//
//        if(license==null||profile==null){
//            return false
//        }
//        else if(license=="Not found"||profile=="Not found"){
//            return false
//        }
//        else{
//            return true
//        }
//    }

}