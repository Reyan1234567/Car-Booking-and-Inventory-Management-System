package com.example.car_booking_and_inventory_management.repositories

import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.data.BookingCarUser
import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarCI
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.data.UserPPLP
import com.example.car_booking_and_inventory_management.data.UsersTable
import com.example.car_booking_and_inventory_management.network.adminApi
import retrofit2.Response

class AdminRepository(private val api: adminApi, private val tokenManager: TokenManager) {
    suspend fun getBookings(): Response<List<BookingCarUser>> {
        return api.getBookings()
    }

    suspend fun getUsers():Response<List<UserPPLP>>{
        return api.getUsers()
    }

    suspend fun getTotalBookings(): Response<Int> {
        return api.getTotalBookings()
    }

    suspend fun getTotalCars(): Response<Int> {
        return api.getTotalCars()
    }

    suspend fun getTotalUsers(): Response<Int> {
        return api.getTotalUsers()
    }

    suspend fun getCars(): Response<List<CarCI>> {
        return api.getCars()
    }

    suspend fun confirm(id:String): Response<String>{
        return api.confirmBooking(id)
    }

    suspend fun cancel(id:String): Response<String>{
        return api.cancelBooking(id)
    }

    suspend fun deleteBooking(id:String): Response<String>{
        return api.deleteBooking(id)
    }

    suspend fun deleteUser(id: String): Response<String> {
        return api.deleteUser(id)
    }
}