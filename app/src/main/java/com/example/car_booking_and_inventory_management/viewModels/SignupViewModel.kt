package com.example.car_booking_and_inventory_management.viewModels

import androidx.lifecycle.ViewModel
import com.example.car_booking_and_inventory_management.data.Signup
import com.example.frontend.repositories.authRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.Result
//import retrofit2.Response


class SignupViewModel(private val repository: authRepository): ViewModel() {

    private val _signupResponse=MutableStateFlow<Result<Signup>?>(null)
    val signupResponse:StateFlow<Result<Signup>?> =_signupResponse.asStateFlow()

    fun signup(body:Signup){
        viewModelScope.launch{
            try{
                var response=repository.signup(body)
                if(response.isSuccessful){
                    _signupResponse.value=Result.success(response.body()!!)
                }
                else{
                    _signupResponse.value=Result.failure(Exception("Signup Failed: ${response.errorBody()?.toString()}"))
                }
            }
            catch (e:Exception){

            }
        }
    }
}