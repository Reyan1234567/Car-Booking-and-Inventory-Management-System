package com.example.frontend.network

import com.example.frontend.DataStore.TokenManager
import com.example.frontend.data.LoginResult
import com.example.frontend.data.RefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor



class AuthInterceptor(
    private val tokenManager: TokenManager,
    private val authApi:authApi
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request=chain.request()

        val token=runBlocking{tokenManager.getAccessToken()}
        val modifiedRequest=request.newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()


        val response=chain.proceed(modifiedRequest)

        if(response.code==401){
            response.close()

            val refreshToken=runBlocking{tokenManager.getRefreshToken()}
            if (refreshToken == null) {
                // No refresh token available, user needs to login again
                return response
            }

            val newTokenResponse: retrofit2.Response<LoginResult>?=try{
                runBlocking {
                    authApi.refresh(RefreshRequest(refreshToken))
                }
            }
            catch (e:Exception){
                null
            }
            if (newTokenResponse?.isSuccessful == true && newTokenResponse.body() != null) {
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