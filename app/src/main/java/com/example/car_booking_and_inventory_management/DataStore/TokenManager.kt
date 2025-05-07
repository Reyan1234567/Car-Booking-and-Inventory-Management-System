package com.example.car_booking_and_inventory_management.DataStore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

//Adds a dataStore to every Context, bound to a preferences file named "auth_prefs".
private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val ID= stringPreferencesKey("ID")
        val USER_NAME= stringPreferencesKey("USER_NAME")
        val EMAIL= stringPreferencesKey("EMAIL")
        val PROFILE_PHOTO= stringPreferencesKey("PROFILE_PHOTO")
        val LICENSE_PHOTO= stringPreferencesKey("LICENSE_PHOTO")
        val PHONE_NUMBER= stringPreferencesKey("PHONE_NUMBER")
        val FIRST_NAME= stringPreferencesKey("FIRST_NAME")
        val LAST_NAME= stringPreferencesKey("LAST_NAME")
        val ROLE= stringPreferencesKey("ROLE")
    }

    suspend fun saveUserInfo(id:String, accessToken: String, refreshToken: String, username:String, email:String, phoneNumber:String, profilePhoto:String, licensePhoto:String, firstName:String, lastName:String,role:String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
            it[USER_NAME]=username
            it[ID]=id
            it[EMAIL]=email
            it[PHONE_NUMBER]=phoneNumber
            it[PROFILE_PHOTO]=profilePhoto
            it[LICENSE_PHOTO]=licensePhoto
            it[FIRST_NAME]=firstName
            it[LAST_NAME]=lastName
            it[ROLE]=role
        }
    }

    suspend fun editFromSave(username: String,email: String , phoneNumber: String, licensePhoto: String?, profilePhoto: String?){
        context.dataStore.edit {
            it[USER_NAME]=username
            it[EMAIL]=email
            it[PHONE_NUMBER]=phoneNumber
            it[LICENSE_PHOTO]=licensePhoto?:""
            it[PROFILE_PHOTO]=profilePhoto?:""
        }
    }
    suspend fun saveTokens(accessToken: String, refreshToken: String){
        context.dataStore.edit{
            it[ACCESS_TOKEN]=accessToken
            it[REFRESH_TOKEN]=refreshToken
        }
    }

    suspend fun getUsername(): String? {
        return context.dataStore.data.first()[USER_NAME]
    }

    suspend fun getRole():String?{
        return context.dataStore.data.first()[ROLE]
    }


    suspend fun getuserId(): String? {
        Log.v("TokenManager", "ID: ${context.dataStore.data.first()[ID]}")
        return context.dataStore.data.first()[ID]
    }

    suspend fun getEmail(): String? {
        Log.v("TokenManager", "Email: ${context.dataStore.data.first()[EMAIL]}")
        return context.dataStore.data.first()[EMAIL]
    }

    suspend fun getPhoneNumber(): String? {
        Log.v("TokenManager", "Phone Number: ${context.dataStore.data.first()[PHONE_NUMBER]}")
        return context.dataStore.data.first()[PHONE_NUMBER]
    }
    suspend fun getProfilePhoto(): String? {
        return context.dataStore.data.first()[PROFILE_PHOTO]
    }

    suspend fun getLicensePhoto(): String? {
        Log.v("TokenManager", "License Photo: ${context.dataStore.data.first()[LICENSE_PHOTO]}")
        return context.dataStore.data.first()[LICENSE_PHOTO]
    }

    suspend fun getAccessToken(): String? {
        Log.v("TokenManager", "Access Token: ${context.dataStore.data.first()[ACCESS_TOKEN]}")
        return context.dataStore.data.first()[ACCESS_TOKEN]
    }


    suspend fun getRefreshToken(): String? {
        Log.v("TokenManager", "Refresh Token: ${context.dataStore.data.first()[REFRESH_TOKEN]}")
        return context.dataStore.data.first()[REFRESH_TOKEN]}

    suspend fun clearTokens() {
        context.dataStore.edit {
            it.clear()
        }
    }

}
