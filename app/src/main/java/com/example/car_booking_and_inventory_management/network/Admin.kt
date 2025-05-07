package com.example.car_booking_and_inventory_management.network

import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.data.UsersTable
import retrofit2.Response
import retrofit2.http.GET

interface Admin {
    @GET("bookings")
    suspend fun getBookings(): Response<List<BookingTable>>

    @GET("users")
    suspend fun getUsers():Response<List<UsersTable>>

    @GET("total_bookings")
    suspend fun getTotalBookings():Response<Int>

    @GET("total_cars")
    suspend fun getTotalCars():Response<Int>

    @GET("total_users")
    suspend fun getTotalUsers():Response<Int>
}