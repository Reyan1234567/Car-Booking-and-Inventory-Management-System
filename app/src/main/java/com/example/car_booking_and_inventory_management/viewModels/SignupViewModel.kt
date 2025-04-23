package com.example.car_booking_and_inventory_management.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.repositories.authRepository
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Result
//import retrofit2.Response

@HiltViewModel
class SignupViewModel @Inject constructor(private val repository: authRepository): ViewModel() {

    private val _signupResponse=MutableStateFlow<Result<Signup>?>(null)
    val signupResponse:StateFlow<Result<Signup>?> =_signupResponse.asStateFlow()

    var isLoading = MutableStateFlow(false)

     var firstname by mutableStateOf("")
     var lastname by mutableStateOf("")
     var birthDate by mutableStateOf("")
     var phoneNumber by mutableStateOf("")
     var email by mutableStateOf("")
     var username by mutableStateOf("")
     var password by mutableStateOf("")

    fun updateFirstname(value:String){
        firstname=value
    }
    fun updateLastname(value:String){
        lastname=value
    }

    fun updateBirthDate(value:String){
        birthDate=value
    }

    fun updatePhoneNumber(value:String){
        phoneNumber=value
    }

    fun updateEmail(value:String){
        email=value
    }

    fun updatePassword(value:String){
        password=value
    }

    fun updateUsername(value:String){
        username=value
    }

    fun signup(body:Signup){
        viewModelScope.launch{
            try{
                isLoading.value=true
                Log.v(TAG, "Inside the function")
                val response=repository.signup(body)
                if(response.isSuccessful){
                    Log.v(TAG, "Inside the successful function")
                    _signupResponse.value=Result.success(response.body()!!)
                }
                else{
                    Log.v(TAG, "Inside the failure function")
                    _signupResponse.value=Result.failure(Exception("Signup Failed: ${response.errorBody()?.toString()}"))
                }
            }
            catch (e:Exception){
                Log.e(TAG, "Exception during signup:catch",e)
                _signupResponse.value=Result.failure(e)
            }
            finally {
                isLoading.value=false
            }
        }
    }
}