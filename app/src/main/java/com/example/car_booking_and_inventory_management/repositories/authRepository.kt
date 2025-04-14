package com.example.frontend.repositories

import com.example.frontend.data.LoginInput
import com.example.frontend.data.LoginResult
import com.example.frontend.network.authApi
import retrofit2.Response

class authRepository (private val api: authApi) {
    suspend fun login(body:LoginInput):Response<LoginResult>{
        return api.login(body)
    }
}