package com.example.frontend.repositories

import com.example.car_booking_and_inventory_management.data.Signup
import com.example.frontend.DataStore.TokenManager
import com.example.frontend.data.LoginInput
import com.example.frontend.data.LoginResult
import com.example.frontend.network.authApi
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class authRepository @Inject constructor(private val api: authApi,  private val tokenManager: TokenManager){

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

    suspend fun login(body:LoginInput):Response<LoginResult>{
        return api.login(body)
    }

    suspend fun signup(body:Signup):Response<Signup>{
        return api.signup(body)
    }

}
