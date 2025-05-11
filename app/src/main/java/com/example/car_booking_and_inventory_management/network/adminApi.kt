package com.example.car_booking_and_inventory_management.network

import com.example.car_booking_and_inventory_management.data.BookingCarUser
import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarCI
import com.example.car_booking_and_inventory_management.data.CarPost
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.data.UploadResponse
import com.example.car_booking_and_inventory_management.data.UserPPLP
import com.example.car_booking_and_inventory_management.data.UsersTable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.PATCH

interface adminApi {
    @GET("bookings")
    suspend fun getBookings(): Response<List<BookingCarUser>>

    @GET("users")
    suspend fun getUsers():Response<List<UserPPLP>>

    @GET("total_bookings")
    suspend fun getTotalBookings():Response<Int>

    @GET("total_cars")
    suspend fun getTotalCars():Response<Int>

    @GET("total_users")
    suspend fun getTotalUsers():Response<Int>

    @GET("cars")
    suspend fun getCars(): Response<List<CarCI>>

    @GET("confirm")
    suspend fun confirmBooking(@Query("_id") _id:String): Response<String>

    @GET("cancel")
    suspend fun cancelBooking(@Query("id") _id:String): Response<String>

    @DELETE("booking/{id}")
    suspend fun deleteBooking(@Path("id") _id:String): Response<ResponseBody>

    @DELETE("user/{id}")
    suspend fun deleteUser(@Path("id") _id: String): Response<ResponseBody>

    @DELETE("car/{id}")
    suspend fun deleteCar(@Path("id") _id: String): Response<ResponseBody>

    @Multipart
    @POST("carImageUpload")
    suspend fun uploadCar(
        @Part image: MultipartBody.Part?
    ):Response<UploadResponse>

    @POST("cars")
    suspend fun createCar(@Body car: CarPost): Response<CarResponse>

    @PATCH("cars/{id}")
    suspend fun updateCar(
        @Path("id") id: String,
        @Body car: CarPost
    ): Response<CarResponse>


}