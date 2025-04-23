package com.example.car_booking_and_inventory_management.network

import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.data.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface authApi {
    @POST("auth/signin")
    suspend fun login(@Body user: LoginInput): Response<LoginResult>

    @POST("auth/refresh")
    suspend fun refresh(@Body refreshToken: RefreshRequest): Response<LoginResult>

    @POST("auth/signup")
    suspend fun signup(@Body userInfo: Signup): Response<Signup>

}


//    companion object{
//        private const val BASE_URL="https://localhost:4000/"
//
//        fun provideRetrofit(tokenManager:TokenManager):Retrofit{
//            val authApi=Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(authApi::class.java)
//
//            val client= OkHttpClient.Builder()
//                .addInterceptor(AuthInterceptor(tokenManager,authApi))
//                .build()
//
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//
//        }
//
//    }