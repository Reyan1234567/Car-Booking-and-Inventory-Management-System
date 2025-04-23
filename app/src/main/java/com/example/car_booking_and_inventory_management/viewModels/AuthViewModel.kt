package com.example.car_booking_and_inventory_management.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.repositories.authRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.Result
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: authRepository): ViewModel(){
    private val _loginResult= MutableStateFlow<Result<LoginResult>?>(null)
    val loginResult: StateFlow<Result<LoginResult>?> = _loginResult.asStateFlow()

    var isLoading =MutableStateFlow(false)
            fun login(user: LoginInput){
                viewModelScope.launch{
                    try{
                        isLoading.value = true
                        val response=repository.login(user)
                        if(response.isSuccessful && response.body() != null){
                            _loginResult.value=Result.success(response.body()!!)
                            response.body()?.let {repository.saveTokens(it.accessToken,it.refreshToken)}
                        }
                        else{
                            val errorMessage = when(response.code()) {
                                401 -> "Invalid username or password"
                                403 -> "Account is locked"
                                404 -> "User not found"
                                500 -> "Server error, please try again later"
                                else -> "Login failed: ${response.message()}"
                            }
                            _loginResult.value=Result.failure(Exception(errorMessage))
                        }
                    }
                    catch(e:Exception){
                        Log.e(TAG,"some error",e)
                        _loginResult.value=Result.failure(Exception("Network error: ${e.message}"))
                    }
                    finally {
                        isLoading.value = false
                    }
                }
            }
}