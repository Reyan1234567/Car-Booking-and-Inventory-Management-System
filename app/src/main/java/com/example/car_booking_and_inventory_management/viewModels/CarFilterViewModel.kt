package com.example.car_booking_and_inventory_management.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.car_booking_and_inventory_management.data.Location
import com.example.car_booking_and_inventory_management.repositories.CarFilterRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.car_booking_and_inventory_management.data.BookingRequest
import com.example.car_booking_and_inventory_management.data.BookingResponse
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarFilters
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.data.Username
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@HiltViewModel
class CarFilterViewModel @Inject constructor(private val repository: CarFilterRepository):
    ViewModel() {

    private val _carsGetResponse= MutableStateFlow<Result<List<Location>>?>(null)
    val carsGetResponse: StateFlow<Result<List<Location>>?> =_carsGetResponse.asStateFlow()

    private val _carsGetResponse1= MutableStateFlow<Result<List<Location>>?>(null)
    val carsGetResponse1: StateFlow<Result<List<Location>>?> =_carsGetResponse1.asStateFlow()

    private val _carsFilterResponse= MutableStateFlow<Result<List<CarResponse>>?>(null)
    val carsFilterResponse: StateFlow<Result<List<CarResponse>>?> =_carsFilterResponse.asStateFlow()


    private val _legitmacyResponse= MutableStateFlow<Result<Username>?>(null)
    val legitmacyResponse:StateFlow<Result<Username>?> =_legitmacyResponse.asStateFlow()

    private val _bookingCreationResponse= MutableStateFlow<Result<BookingResponse>?>(null)
    val bookingCreationResponse:StateFlow<Result<BookingResponse>?> =_bookingCreationResponse.asStateFlow()

    private val _historyResponse=MutableStateFlow<Result<List<BookingResponse>>?>(null)
    val historyResponse:StateFlow<Result<List<BookingResponse>>?> =_historyResponse.asStateFlow()

    var isLoading by mutableStateOf(false)

    var id by mutableStateOf("")
    var pickUp by  mutableStateOf("Click to select destination")
    var dropOff by  mutableStateOf("Click to select destination")
    var startDate by  mutableStateOf("Click to select Date")
    var startTime by  mutableStateOf("Click to select time")
    var endDate by  mutableStateOf("Click to select Date")
    var endTime by  mutableStateOf("Click to select time")
    var dailyRate by mutableStateOf(0)
    var showDropOff by mutableStateOf(false)
    private set

    fun changeShowDropOff(value:Boolean){
        showDropOff=value
    }

    fun updatePickUp(pickup:String){
        pickUp=pickup
    }

    fun updateDropOff(dropoff:String){
        dropOff=dropoff
    }

    fun updateStartDate(startdate:String){
        startDate=startdate
    }

    fun updateStartTime(starttime:String){
        startTime=starttime
    }

    fun updateEndDate(enddate:String){
        endDate=enddate
    }

    fun updateEndTime(endtime:String){
        endTime=endtime
    }


    fun getLocations(query:String){
        viewModelScope.launch{
            try{
                val result=repository.getLocations(query)
               if(result.isSuccessful){
                    _carsGetResponse.value=Result.success(result.body()!!)
               }
                else{
                    _carsGetResponse.value=Result.failure(Exception("${result.errorBody()?.string()}") )
               }
            }
            catch(e:Exception){
                Log.v(TAG, "inside the catch statement")
                _carsGetResponse.value=Result.failure(e)

            }
        }
    }

    fun getLocations1(query:String){
        viewModelScope.launch{
            try{
                val result=repository.getLocations(query)
                if(result.isSuccessful){
                    _carsGetResponse1.value=Result.success(result.body()!!)
                }
                else{
                    _carsGetResponse1.value=Result.failure(Exception("${result.errorBody()?.string()}") )
                }
            }
            catch(e:Exception){
                Log.v(TAG, "inside the catch statement${e}")
                _carsGetResponse1.value=Result.failure(e)

            }
        }
    }

    fun getFilteredCars(filters:CarFilters){
        viewModelScope.launch {
            try{
                isLoading=true
                val result=repository.getFilteredCars(filters)
                if(result.isSuccessful){
                    _carsFilterResponse.value=Result.success(result.body()!!)
                }
                else{
                    _carsFilterResponse.value=Result.failure((Exception("${result.errorBody()?.string()}")))
                }
            }
            catch(e:Exception){
                _carsFilterResponse.value=Result.failure(e)
            }
            finally {
                isLoading=false
            }
        }
    }

    fun checkLegitimacy(username: String){
        viewModelScope.launch {
            try {
                val result=repository.checkLegitimacy(username)
                if(result.isSuccessful && result.body()!=null){
                    _legitmacyResponse.value=Result.success(result.body()!!)
                }
                else{
                    _legitmacyResponse.value=Result.failure(Exception(result.errorBody()?.string()))
                }
            }
            catch (e:Exception){
                _legitmacyResponse.value=Result.failure(e)
            }
        }
    }

    suspend fun getUsername():String?{
            return repository.getUsername()
    }


    fun createBooking(booking: BookingRequest) {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = repository.createBooking(booking)
                if (result.isSuccessful && result.body() != null) {
                    _bookingCreationResponse.value = Result.success(result.body()!!)
                    // Reset form fields after successful booking
                    pickUp = "Click to select destination"
                    dropOff = "Click to select destination"
                    startDate = "Click to select Date"
                    startTime = "Click to select time"
                    endDate = "Click to select Date"
                    endTime = "Click to select time"
                } else {
                    _bookingCreationResponse.value = Result.failure(
                        Exception(result.errorBody()?.string() ?: "Booking creation failed")
                    )
                }
            } catch (e: Exception) {
                _bookingCreationResponse.value = Result.failure(e)
            } finally {
                isLoading = false
            }
        }
    }

    suspend fun getuserId():String?{
       return repository.getuserId()
    }

    fun getHistory(id:String){
        viewModelScope.launch {
            try {
                val result=repository.getHistory(id)
                if(result.isSuccessful && result.body()!=null){
                    _historyResponse.value=Result.success(result.body()!!)
                }
                else{
                    _historyResponse.value=Result.failure(Exception(result.errorBody()?.string()))
                }
            }
            catch (e:Exception){
                _historyResponse.value=Result.failure(e)
            }
        }
    }

}