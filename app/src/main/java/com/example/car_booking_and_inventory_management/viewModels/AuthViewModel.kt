package com.example.frontend.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.LoginInput
import com.example.frontend.data.LoginResult
import com.example.frontend.repositories.authRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.Result
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val repository:authRepository): ViewModel(){
    private val _loginResult= MutableStateFlow<Result<LoginResult>?>(null)
    val loginResult: StateFlow<Result<LoginResult>?> = _loginResult.asStateFlow()

    var isLoading =MutableStateFlow(false)

            fun login(user:LoginInput){
                viewModelScope.launch{
                    try{
                        val response=repository.login(user)
                        isLoading.value=true
                        if(response.isSuccessful){
                            _loginResult.value=Result.success(response.body()!!)
                        }
                        else{
                            _loginResult.value=Result.failure(Exception("Login failed:${response.code()}"))
                        }
                        isLoading.value=false
                    }
                    catch(e:Exception){
                        _loginResult.value=Result.failure(e)
                    }
                }
            }
}