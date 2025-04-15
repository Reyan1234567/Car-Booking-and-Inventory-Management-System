package com.example.frontend.repositories

import com.example.car_booking_and_inventory_management.data.Signup
import com.example.frontend.data.LoginInput
import com.example.frontend.data.LoginResult
import com.example.frontend.network.authApi
import retrofit2.Response
import retrofit2.Retrofit

class authRepository(private val api: authApi) {
    suspend fun login(body:LoginInput):Response<LoginResult>{
        return api.login(body)
    }

    suspend fun signup(body:Signup):Response<Signup>{
        return api.signup(body)
    }
}