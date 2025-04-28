package com.example.car_booking_and_inventory_management.repositories

import com.example.car_booking_and_inventory_management.DataStore.TokenManager
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

    suspend fun getUsername():String?{
        return tokenManager.getUsername()
    }

    suspend fun checkLegitimacy(username:String):Response<Boolean>{
        return api.checkLegitimacy(username)
    }
}