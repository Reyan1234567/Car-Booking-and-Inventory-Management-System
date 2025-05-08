package com.example.car_booking_and_inventory_management.network

import com.example.car_booking_and_inventory_management.data.BookingRequest
import com.example.car_booking_and_inventory_management.data.BookingResponse
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarFilters
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.data.Location
import com.example.car_booking_and_inventory_management.data.Username
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface searchApi {

    @GET("api/cars")
    suspend fun getAllCars(): Response<List<Car>>

    @GET("api/locations")
    suspend fun getLocations(@Query("search") search:String):Response<List<Location>>

    @POST("api/filteredCars")
    suspend fun getFilteredCars(@Body filters:CarFilters):Response<List<CarResponse>>

    @POST("api/booking")
    suspend fun createBooking(@Body booking: BookingRequest):Response<BookingResponse>

    @GET("api/checkLegitimacy")
    suspend fun checkLegitimacy(@Query("username") username:String):Response<Username>

    @GET("history")
    suspend fun getHistory(@Query("id") id:String):Response<List<BookingResponse>>


}