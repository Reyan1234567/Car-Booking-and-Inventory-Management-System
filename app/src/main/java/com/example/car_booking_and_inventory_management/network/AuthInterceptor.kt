package com.example.car_booking_and_inventory_management.network

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
        var request=chain.request()

        val token=runBlocking{tokenManager.getAccessToken()}
        if(token==null){
            return chain.proceed(request)
        }

        val modifiedRequest=request.newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()


        val response=chain.proceed(modifiedRequest)

        val errorBody=try{
            val errorJson=response.peekBody(Long.MAX_VALUE).string()
            JSONObject(errorJson).getString("error")
        }
        catch (e:Exception){
            null
        }

        if(response.code==401 && errorBody=="Unauthorized - Token expired"){
            response.close()

            val refreshToken=runBlocking{tokenManager.getRefreshToken()}
            if (refreshToken == null) {
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
                return response
            }

            if (newTokenResponse.isSuccessful == true) {
                runBlocking{
                    newTokenResponse.body()?.let {tokenManager.saveTokens(it.accessToken, it.refreshToken)}
                }
                val at = newTokenResponse.body()?.accessToken
                if (at != null) {
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