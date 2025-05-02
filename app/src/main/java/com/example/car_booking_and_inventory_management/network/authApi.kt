package com.example.car_booking_and_inventory_management.network

import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.data.Refresh
import com.example.car_booking_and_inventory_management.data.RefreshRequest
import com.example.car_booking_and_inventory_management.data.RefreshResult
import com.example.car_booking_and_inventory_management.data.UploadResponse
import com.example.car_booking_and_inventory_management.data.Username
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface authApi {
    @POST("auth/signin")
    suspend fun login(@Body user: LoginInput): Response<LoginResult>

    @POST("auth/refresh")
    suspend fun refresh(@Body refreshToken: Refresh): Response<RefreshResult>

    @POST("auth/signup")
    suspend fun signup(@Body userInfo: Signup): Response<Signup>

    @GET("checkAccessToken")
    suspend fun checkAccessToken():Response<LoginResult>

    @Multipart
    @POST("upload")
    suspend fun uploadProfile(
        @Part image:MultipartBody.Part
    ):Response<UploadResponse>
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