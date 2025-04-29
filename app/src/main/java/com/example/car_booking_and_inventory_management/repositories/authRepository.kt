package com.example.car_booking_and_inventory_management.repositories

import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.data.Refresh
import com.example.car_booking_and_inventory_management.data.RefreshResult
import com.example.car_booking_and_inventory_management.network.authApi
import retrofit2.Response
import javax.inject.Inject

class authRepository (private val api: authApi, private val tokenManager: TokenManager) {

    suspend fun saveUserInfo(
        accesstToken: String,
        refreshToken: String,
        username: String,
        email: String,
        phoneNumber: String,
        profilePhoto: String,
        licensePhoto: String,
        firstName:String,
        lastName:String
    ) {
        tokenManager.saveUserInfo(
            accesstToken,
            refreshToken,
            username,
            email,
            phoneNumber,
            profilePhoto,
            licensePhoto,
            firstName,
            lastName
        )
    }

//    suspend fun saveUserInfo(username: String) {
//        tokenManager.saveUserInfo(username)
//    }

        suspend fun getAccessToken() {
            tokenManager.getAccessToken()
        }

        suspend fun getRefreshToken() {
            tokenManager.getRefreshToken()
        }

        suspend fun clearTokens() {
            tokenManager.clearTokens()
        }

        suspend fun login(body: LoginInput): Response<LoginResult> {
            return api.login(body)
        }

        suspend fun signup(body: Signup): Response<Signup> {
            return api.signup(body)
        }

        suspend fun refresh(body:Refresh):Response<RefreshResult>{
            return api.refresh(body)
        }

}

