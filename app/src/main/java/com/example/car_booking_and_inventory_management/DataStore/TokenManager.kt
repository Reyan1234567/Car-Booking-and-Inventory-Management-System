package com.example.car_booking_and_inventory_management.DataStore

import android.content.Context
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
        val USER_NAME= stringPreferencesKey("USER_NAME")
        val EMAIL= stringPreferencesKey("EMAIL")
        val PROFILE_PHOTO= stringPreferencesKey("PROFILE_PHOTO")
        val LICENSE_PHOTO= stringPreferencesKey("LICENSE_PHOTO")
        val PHONE_NUMBER= stringPreferencesKey("PHONE_NUMBER")
        val FIRST_NAME= stringPreferencesKey("FIRST_NAME")
        val LAST_NAME= stringPreferencesKey("LAST_NAME")
    }

    suspend fun saveUserInfo(accessToken: String, refreshToken: String, username:String, email:String, phoneNumber:String, profilePhoto:String, licensePhoto:String, firstName:String, lastName:String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
            it[USER_NAME]=username
            it[EMAIL]=email
            it[PHONE_NUMBER]=phoneNumber
            it[PROFILE_PHOTO]=profilePhoto
            it[LICENSE_PHOTO]=licensePhoto
            it[FIRST_NAME]=firstName
            it[LAST_NAME]=lastName
        }
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String){
        context.dataStore.edit{
            it[ACCESS_TOKEN]=accessToken
            it[REFRESH_TOKEN]=refreshToken
        }
    }

    suspend fun getUsername(): String?=
        context.dataStore.data.first()[USER_NAME]

    suspend fun getEmail(): String?=
        context.dataStore.data.first()[EMAIL]

    suspend fun getPhoneNumber(): String?=
        context.dataStore.data.first()[PHONE_NUMBER]

    suspend fun getProfilePhoto(): String?=
        context.dataStore.data.first()[PROFILE_PHOTO]

    suspend fun getLicensePhoto(): String?=
        context.dataStore.data.first()[LICENSE_PHOTO]

    suspend fun getAccessToken(): String?=
        context.dataStore.data.first()[ACCESS_TOKEN]


    suspend fun getRefreshToken(): String? =
        context.dataStore.data.first()[REFRESH_TOKEN]

    suspend fun clearTokens() {
        context.dataStore.edit {
            it.clear()
        }
    }
}
