package com.example.car_booking_and_inventory_management.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.data.Refresh
import com.example.car_booking_and_inventory_management.data.RefreshRequest
import com.example.car_booking_and_inventory_management.data.RefreshResult
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import org.json.JSONObject


class AuthInterceptor(
    private val tokenManager: TokenManager,
    private val authApi: authApi
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        Log.v(TAG, "in the if statement")
        var request=chain.request()

        val token=runBlocking{tokenManager.getAccessToken()}
        if(token==null){
            Log.v(TAG, "shiiiii accesstoken is null")
            return chain.proceed(request)
        }

        val modifiedRequest=request.newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()


        val response=chain.proceed(modifiedRequest)

        val errorBody=try{
            response.peekBody(Long.MAX_VALUE).string()
        }
        catch (e:Exception){
            null
        }

        if(response.code==401 && errorBody=="Unauthorized - Token expired"){
            Log.v(TAG, "in the if statement of the 401")
            response.close()

            val refreshToken=runBlocking{tokenManager.getRefreshToken()}
            Log.v(TAG, refreshToken.toString())
            if (refreshToken == null) {
                Log.v(TAG, "shiiiii refresh refreshtoken is null")
                return response
            }

            val newTokenResponse: retrofit2.Response<RefreshResult>?=try{
                runBlocking {
                    authApi.refresh(Refresh(refreshToken))
                }
            }
            catch (e:Exception){
                null
            }

            if(newTokenResponse?.body()==null){
                Log.v(TAG, "shiiiii new accesstoken is null")
                return response
            }
            Log.v(TAG, "in the if statement of newTokenResponse")

            if (newTokenResponse.isSuccessful == true) {
                Log.v(TAG, "in the if statement of newTokenResponse")
                runBlocking{
                    newTokenResponse.body()?.let {tokenManager.saveTokens(it.accessToken, it.refreshToken)}
                }
                val at = newTokenResponse.body()?.accessToken
                if (at != null) {
                    Log.v(TAG, "in the if statement of accessToken")
                    val retriedRequest=request.newBuilder()
                        .addHeader("Authorization","Bearer $at")
                        .build()
                    return chain.proceed(retriedRequest)
                }
            }
            // If we get here, refresh failed or new token is null
            return response
        }
        return response
    }
}