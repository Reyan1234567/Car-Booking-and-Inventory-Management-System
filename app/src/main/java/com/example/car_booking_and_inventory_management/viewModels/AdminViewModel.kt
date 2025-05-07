package com.example.car_booking_and_inventory_management.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.data.UsersTable
import androidx.lifecycle.viewModelScope
import com.example.car_booking_and_inventory_management.repositories.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(private val repository:AdminRepository): ViewModel() {
    private val _BookingsResponse= MutableStateFlow<Result<BookingTable>?>(null)
    val BookingsResponse: StateFlow<Result<BookingTable>?> =_BookingsResponse.asStateFlow()

    private val _UserResponse = MutableStateFlow<Result<UsersTable>?>(null)
    val UserResponse: StateFlow<Result<UsersTable>?> = _UserResponse.asStateFlow()

    private val _TotalBookingsResponse = MutableStateFlow<Result<Int>?>(null)
    val TotalBookingsResponse: StateFlow<Result<Int>?> = _TotalBookingsResponse.asStateFlow()

    private val _TotalCarsResponse = MutableStateFlow<Result<Int>?>(null)
    val TotalCarsResponse: StateFlow<Result<Int>?> = _TotalCarsResponse.asStateFlow()

    private val _TotalUsersResponse = MutableStateFlow<Result<Int>?>(null)
    val TotalUsersResponse: StateFlow<Result<Int>?> = _TotalUsersResponse.asStateFlow()

    fun getBookings(){
        viewModelScope.launch{
            var result=repository.getBookings()
            try{
                if(result.isSuccessful&&result.body()!=null){
                    _BookingsResponse.value=Result.success(result.body()!!)
                }
                else{
                    _BookingsResponse.value=Result.failure(Exception(result.errorBody()?.string()))
                }
            }
            catch(e:Exception){
                _BookingsResponse.value=Result.failure(e)
                Log.v(TAG, e.toString())
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val result = repository.getUsers()
            try {
                if (result.isSuccessful && result.body() != null) {
                    _UserResponse.value = Result.success(result.body()!!)
                } else {
                    _UserResponse.value = Result.failure(Exception(result.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _UserResponse.value = Result.failure(e)
                Log.v("UserViewModel", e.toString())
            }
        }
    }

    fun getTotalBookings() {
        viewModelScope.launch {
            try {
                val result = repository.getTotalBookings()
                if (result.isSuccessful && result.body() != null) {
                    _TotalBookingsResponse.value = Result.success(result.body()!!)
                } else {
                    _TotalBookingsResponse.value = Result.failure(Exception(result.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _TotalBookingsResponse.value = Result.failure(e)
                Log.v(TAG, e.toString())
            }
        }
    }

    fun getTotalCars() {
        viewModelScope.launch {
            try {
                val result = repository.getTotalCars()
                if (result.isSuccessful && result.body() != null) {
                    _TotalCarsResponse.value = Result.success(result.body()!!)
                } else {
                    _TotalCarsResponse.value = Result.failure(Exception(result.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _TotalCarsResponse.value = Result.failure(e)
                Log.v(TAG, e.toString())
            }
        }
    }

    fun getTotalUsers() {
        viewModelScope.launch {
            try {
                val result = repository.getTotalUsers()
                if (result.isSuccessful && result.body() != null) {
                    _TotalUsersResponse.value = Result.success(result.body()!!)
                } else {
                    _TotalUsersResponse.value = Result.failure(Exception(result.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _TotalUsersResponse.value = Result.failure(e)
                Log.v(TAG, e.toString())
            }
        }
    }
}