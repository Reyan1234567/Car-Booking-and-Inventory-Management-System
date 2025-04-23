package com.example.car_booking_and_inventory_management

import android.content.Context
import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.network.AuthInterceptor
import com.example.car_booking_and_inventory_management.network.authApi
import com.example.car_booking_and_inventory_management.repositories.authRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }


    @Provides
    fun providesRetrofit(okHttpClient:OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.157.147:4000/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun providesApi(retrofit: Retrofit): authApi {
        return retrofit.create(authApi::class.java)
    }


//    @Provides
//    @Singleton
//    fun provideAuthApi(tokenManager: TokenManager): authApi {
//        return authApi.provideRetrofit(tokenManager).create(authApi::class.java)
//    }
//
    @Provides
    @Singleton
    fun provideAuthRepository(api: authApi, tokenManager: TokenManager): authRepository {
        return authRepository(api,tokenManager)
    }
//
//}

//@Provides
//@Singleton // AuthInterceptor should typically be a singleton
//fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
//    // Assuming your AuthInterceptor constructor takes TokenManager
//    return AuthInterceptor(tokenManager)
//}


@Provides
@Singleton
fun provideAuthInterceptor(
    tokenManager: TokenManager
): AuthInterceptor {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:4000/") // ⚠️ this needs to be real when deployed
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi = retrofit.create(authApi::class.java)
    return AuthInterceptor(tokenManager, authApi)
}}