package com.example.car_booking_and_inventory_management.repositories

import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.network.authApi
import retrofit2.Response
import javax.inject.Inject

class authRepository (private val api: authApi, private val tokenManager: TokenManager){

    suspend fun saveTokens(accesstToken:String, refreshToken:String){
        tokenManager.saveTokens(accesstToken,refreshToken)
    }

    suspend fun getAccessToken(){
        tokenManager.getAccessToken()
    }

    suspend fun getRefreshToken(){
        tokenManager.getRefreshToken()
    }

    suspend fun clearTokens(){
        tokenManager.clearTokens()
    }

    suspend fun login(body: LoginInput):Response<LoginResult>{
        return api.login(body)
    }

    suspend fun signup(body: Signup):Response<Signup>{
        return api.signup(body)
    }

}
