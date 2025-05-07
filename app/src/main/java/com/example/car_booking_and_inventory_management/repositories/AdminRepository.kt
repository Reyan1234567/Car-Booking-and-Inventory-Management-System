package com.example.car_booking_and_inventory_management.repositories

import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.data.UsersTable
import com.example.car_booking_and_inventory_management.network.Admin
import retrofit2.Response

class AdminRepository(val admin:Admin) {
    suspend fun getBookings(): Response<List<BookingTable>> {
        return admin.getBookings()
    }

    suspend fun getUsers():Response<List<UsersTable>>{
        return admin.getUsers()
    }

    suspend fun getTotalBookings(): Response<Int> {
        return admin.getTotalBookings()
    }

    suspend fun getTotalCars(): Response<Int> {
        return admin.getTotalCars()
    }

    suspend fun getTotalUsers(): Response<Int> {
        return admin.getTotalUsers()
    }
}