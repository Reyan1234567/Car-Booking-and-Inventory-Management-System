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
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun getAccessToken(): String? =
        context.dataStore.data.first()[ACCESS_TOKEN]

    suspend fun getRefreshToken(): String? =
        context.dataStore.data.first()[REFRESH_TOKEN]

    suspend fun clearTokens() {
        context.dataStore.edit {
            it.clear()
        }
    }
}
