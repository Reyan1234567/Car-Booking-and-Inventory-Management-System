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
import com.example.car_booking_and_inventory_management.data.Booking
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@HiltViewModel
class CarFilterViewModel @Inject constructor(private val repository: CarFilterRepository):
    ViewModel() {

    private val _createBooking= MutableStateFlow<Result<Booking>?>(null)
    val createBooking: StateFlow<Result<Booking>?> =_createBooking.asStateFlow()

    private val _carsGetResponse= MutableStateFlow<Result<List<Location>>?>(null)
    val carsGetResponse: StateFlow<Result<List<Location>>?> =_carsGetResponse.asStateFlow()

    private val _carsGetResponse1= MutableStateFlow<Result<List<Location>>?>(null)
    val carsGetResponse1: StateFlow<Result<List<Location>>?> =_carsGetResponse1.asStateFlow()

    private val _carsFilterResponse= MutableStateFlow<Result<List<Car>>?>(null)
    val carsFilterResponse: StateFlow<Result<List<Car>>?> =_carsFilterResponse.asStateFlow()


    private val _legitimacyResponse= MutableStateFlow<Boolean?>(null)
    val legitimacyResponse:StateFlow<Boolean?> =_legitimacyResponse.asStateFlow()

    var isLoading by mutableStateOf(false)

    var pickUp by  mutableStateOf("Select destination")
    var dropOff by  mutableStateOf("Select destination")
    var startDate by  mutableStateOf("01/11/2025")
    var startTime by  mutableStateOf("09:00 am")
    var endDate by  mutableStateOf("01/11/2025")
    var endTime by  mutableStateOf("09:00 am")

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

    fun checkLegitimacy(){
        val result= runBlocking{ repository.checkLegitimacy() }
        if(result){
            _legitimacyResponse.value=true
        }
        else{
            _legitimacyResponse.value=false
        }
    }

//    suspend fun getUsername():String?{
//            return repository.getUsername()
//
//    }


    fun createBooking(booking: Booking){
        viewModelScope.launch{
            try{
                val result=repository.createBooking(booking)
              if(result.isSuccessful){
                  _createBooking.value=Result.success(result.body()!!)
              }
              else{
              _createBooking.value=Result.failure(Exception("some error shit, I am burnt out!!!!"))
              }
            }
            catch(e:Exception){
                _createBooking.value=Result.failure(e)
            }
        }
    }
}