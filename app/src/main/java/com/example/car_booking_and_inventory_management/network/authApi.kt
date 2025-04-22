package com.example.frontend.network

import androidx.compose.animation.core.rememberTransition
import com.example.car_booking_and_inventory_management.data.Signup
import com.example.frontend.DataStore.TokenManager
import com.example.frontend.data.LoginInput
import com.example.frontend.data.LoginResult
import com.example.frontend.data.RefreshRequest
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface authApi {
    @POST("login")
    suspend fun login(@Body user:LoginInput):Response<LoginResult>

    @POST("auth/refresh")
    suspend fun refresh(@Body refreshToken: RefreshRequest): Response<LoginResult>

    @POST("auth/signup")
    suspend fun signup(@Body userInfo:Signup):Response<Signup>

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