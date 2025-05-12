package com.example.car_booking_and_inventory_management

import android.content.Context
import com.example.car_booking_and_inventory_management.DataStore.TokenManager
import com.example.car_booking_and_inventory_management.network.AuthInterceptor
import com.example.car_booking_and_inventory_management.network.adminApi
import com.example.car_booking_and_inventory_management.network.authApi
import com.example.car_booking_and_inventory_management.network.searchApi
import com.example.car_booking_and_inventory_management.repositories.AdminRepository
import com.example.car_booking_and_inventory_management.repositories.CarFilterRepository
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

    const val BASE_URL="http://10.0.2.2:4000/"

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
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun providesApi(retrofit: Retrofit): authApi {
        return retrofit.create(authApi::class.java)
    }

    @Provides
    fun providesSearchApi(retrofit: Retrofit): searchApi {
        return retrofit.create(searchApi::class.java)
    }

    @Provides
    fun provideAdminApi(retrofit: Retrofit): adminApi {
        return retrofit.create(adminApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: authApi, tokenManager: TokenManager): authRepository {
        return authRepository(api,tokenManager)
    }

    @Provides
    @Singleton
    fun provideLocationFilterRepository(api: searchApi, tokenManager: TokenManager): CarFilterRepository {
        return CarFilterRepository(api, tokenManager)
    }

    @Provides
    @Singleton
    fun provideAdminRepository(api:adminApi, tokenManager: TokenManager):AdminRepository{
        return AdminRepository(api, tokenManager)
    }


@Provides
@Singleton
fun provideAuthInterceptor(
    tokenManager: TokenManager
): AuthInterceptor {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi = retrofit.create(authApi::class.java)
    return AuthInterceptor(tokenManager, authApi)
}}
