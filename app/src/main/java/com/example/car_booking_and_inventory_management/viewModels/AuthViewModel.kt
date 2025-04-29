package com.example.car_booking_and_inventory_management.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.data.Signup
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

    private val _signupResponse=MutableStateFlow<Result<Signup>?>(null)
    val signupResponse:StateFlow<Result<Signup>?> =_signupResponse.asStateFlow()

    var isLoading1 = MutableStateFlow(false)
////////////////////////////////////////////////////////////////////
    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading2 =MutableStateFlow(false)

 ///////////////////////////////////////////////////////////////////
            fun login(user: LoginInput){
                viewModelScope.launch{
                    try{
                        isLoading1.value = true
                        val response=repository.login(user)
                        if(response.isSuccessful && response.body() != null){
                            _loginResult.value=Result.success(response.body()!!)
                            Log.v(TAG, response.body().toString())
                            response.body()?.let {
                                repository.saveUserInfo(
                                    accesstToken = it.accessToken,
                                    refreshToken =it.refreshToken,
                                    username = it.user.username,
                                    email = it.user.email,
                                    phoneNumber = it.user.phoneNumber,
                                    profilePhoto = it.user.profilePhoto,
                                    licensePhoto = it.user.licensePhoto,
                                    firstName=it.user.firstName,
                                    lastName=it.user.lastName
                                )
                            }

                        }
                        else{
                            _loginResult.value=Result.failure(Exception(response.errorBody()?.string()))
                            Log.v(TAG,"${ response.errorBody()?.string()}")
                        }
                    }
                    catch(e:Exception){
                        _loginResult.value=Result.failure(Exception("Network error: ${e.message}"))
                    }
                    finally {
                        isLoading1.value = false
                    }
                }
            }
////////////////////////////////////////////////////////////
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
                isLoading2.value=true
                Log.v(TAG, "Inside the function")
                val response=repository.signup(body)
                if(response.isSuccessful && response.body()!=null){
                    Log.v(TAG, "Inside the successful function")
                    _signupResponse.value=Result.success(response.body()!!)
                }
                else{
                    Log.v(TAG, "Inside the failure function")
                    _signupResponse.value=Result.failure(Exception("Signup Failed: ${response.errorBody()?.string()}"))
                }
            }
            catch (e:Exception){
                Log.e(TAG, "Exception during signup:catch",e)
                _signupResponse.value=Result.failure(e)
            }
            finally {
                isLoading2.value=false
            }
        }
    }
}