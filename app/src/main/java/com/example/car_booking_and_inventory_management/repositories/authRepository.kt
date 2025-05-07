package com.example.car_booking_and_inventory_management.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.data.ProfilePageRequest
import com.example.car_booking_and_inventory_management.data.ProfilePageResult
import com.example.car_booking_and_inventory_management.data.Refresh
import com.example.car_booking_and_inventory_management.data.RefreshResult
import com.example.car_booking_and_inventory_management.data.UploadResponse
import com.example.car_booking_and_inventory_management.data.Username
import com.example.car_booking_and_inventory_management.data.accountEdit
import com.example.car_booking_and_inventory_management.data.saveResponse
import com.example.car_booking_and_inventory_management.network.authApi
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class authRepository (private val api: authApi, private val tokenManager: TokenManager) {

    suspend fun saveUserInfo(
        id:String,
        accessToken: String,
        refreshToken: String,
        username: String,
        email: String,
        phoneNumber: String,
        profilePhoto: String,
        licensePhoto: String,
        firstName:String,
        lastName:String,
        role:String
    ) {
        tokenManager.saveUserInfo(
            id,
            accessToken,
            refreshToken,
            username,
            email,
            phoneNumber,
            profilePhoto,
            licensePhoto,
            firstName,
            lastName,
            role
        )
    }

    suspend fun editFromSave(username: String,email: String,phoneNumber: String,licensePhoto: String?,profilePhoto: String?){
        tokenManager.editFromSave(username,email,phoneNumber,licensePhoto,profilePhoto)
    }

    suspend fun getRole():String?{
        return tokenManager.getRole()
    }

        suspend fun getAccessToken() {
            tokenManager.getAccessToken()
        }
        suspend fun getProfilePhoto():String? {
            Log.v(TAG,"getprofile from the repo's function ${tokenManager.getProfilePhoto().toString()}")
            return tokenManager.getProfilePhoto()
        }

        suspend fun getLicensePhoto():String? {
            return tokenManager.getLicensePhoto()
        }

        suspend fun getId():String? {
            Log.v(TAG,"getId from the repo's function ${tokenManager.getuserId().toString()}")
            return tokenManager.getuserId()
        }

        suspend fun getRefreshToken() {
            tokenManager.getRefreshToken()
        }

        suspend fun getUsername():String? {
            return tokenManager.getUsername()
//            Log.v(TAG,"from the repo's function ${tokenManager.getUsername().toString()}")
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

        suspend fun checkAT():Response<LoginResult>{
            return api.checkAccessToken()
        }

        suspend fun uploadProfile(image:MultipartBody.Part):Response<UploadResponse>{
            return api.uploadProfile(image)
        }

    suspend fun uploadLicense(image:MultipartBody.Part):Response<UploadResponse>{
        return api.uploadLicense(image)
    }

    suspend fun getEmail(): String? {
        return tokenManager.getEmail()
    }

    suspend fun getPhoneNumber(): String? {
        return tokenManager.getPhoneNumber()
    }

    suspend fun editAccount(id:String, body: accountEdit):Response<saveResponse>{
        return api.editAccount(id,body)
    }

    suspend fun logout(){
        tokenManager.clearTokens()
    }

}

