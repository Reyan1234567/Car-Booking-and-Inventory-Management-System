package com.example.car_booking_and_inventory_management.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.data.UsersTable
import androidx.lifecycle.viewModelScope
import com.example.car_booking_and_inventory_management.data.BookingCarUser
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarCI
import com.example.car_booking_and_inventory_management.data.CarPost
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.data.UploadResponse
import com.example.car_booking_and_inventory_management.data.User
import com.example.car_booking_and_inventory_management.data.UserPPLP
import com.example.car_booking_and_inventory_management.repositories.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val repository:AdminRepository): ViewModel() {
    private val _BookingsResponse= MutableStateFlow<Result<List<BookingCarUser>>?>(null)
    val BookingsResponse: StateFlow<Result<List<BookingCarUser>>?> =_BookingsResponse.asStateFlow()

    private val _UserResponse = MutableStateFlow<Result<List<UserPPLP>>?>(null)
    val UserResponse: StateFlow<Result<List<UserPPLP>>?> = _UserResponse.asStateFlow()

    private val _TotalBookingsResponse = MutableStateFlow<Result<Int>?>(null)
    val TotalBookingsResponse: StateFlow<Result<Int>?> = _TotalBookingsResponse.asStateFlow()

    private val _TotalCarsResponse = MutableStateFlow<Result<Int>?>(null)
    val TotalCarsResponse: StateFlow<Result<Int>?> = _TotalCarsResponse.asStateFlow()

    private val _TotalUsersResponse = MutableStateFlow<Result<Int>?>(null)
    val TotalUsersResponse: StateFlow<Result<Int>?> = _TotalUsersResponse.asStateFlow()

    private val _CarsResponse = MutableStateFlow<Result<List<CarCI>>?>(null)
    val CarsResponse: StateFlow<Result<List<CarCI>>?> = _CarsResponse.asStateFlow()

    private val _cancelResult=MutableStateFlow<Result<String>?>(null)
    val cancelResult:StateFlow<Result<String>?> = _cancelResult.asStateFlow()

    private val _confirmResult=MutableStateFlow<Result<String>?>(null)
    val confirmResult:StateFlow<Result<String>?> = _confirmResult.asStateFlow()

    private val _deleteResult=MutableStateFlow<Result<String>?>(null)
    val deleteResult:StateFlow<Result<String>?> = _deleteResult.asStateFlow()

    private val _deleteUserResult = MutableStateFlow<Result<String>?>(null)
    val deleteUserResult: StateFlow<Result<String>?> = _deleteUserResult.asStateFlow()

    private val _deleteCarResult = MutableStateFlow<Result<String>?>(null)
    val deleteCarResult: StateFlow<Result<String>?> = _deleteCarResult.asStateFlow()

    private val _createCarResult = MutableStateFlow<Result<CarResponse>?>(null)
    val createCarResult: StateFlow<Result<CarResponse>?> = _createCarResult.asStateFlow()

    private val _updateCarResult = MutableStateFlow<Result<CarResponse>?>(null)
    val updateCarResult: StateFlow<Result<CarResponse>?> = _updateCarResult.asStateFlow()

    private val _uploadCar = MutableStateFlow<Result<UploadResponse>?>(null)
    val uploadCar: StateFlow<Result<UploadResponse>?> = _uploadCar.asStateFlow()

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

    fun getCars() {
        viewModelScope.launch {
            try {
                val result = repository.getCars()
                if (result.isSuccessful && result.body() != null) {
                    _CarsResponse.value = Result.success(result.body()!!)
                } else {
                    _CarsResponse.value = Result.failure(Exception(result.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _CarsResponse.value = Result.failure(e)
                Log.v(TAG, e.toString())
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

    fun cancel(bookingId: String) {
    viewModelScope.launch {
        try {
            val result = repository.cancel(bookingId)
            if (result.isSuccessful && result.body() != null) {
                _cancelResult.value = Result.success("Booking canceled successfully")
            } else {
                _cancelResult.value = Result.failure(Exception(result.errorBody()?.string()))
            }
        } catch (e: Exception) {
            _cancelResult.value = Result.failure(e)
            Log.v(TAG, "Error canceling booking: ${e.message}")
        }
    }
}

fun confirm(bookingId: String) {
    viewModelScope.launch {
        try {
            val result = repository.confirm(bookingId)
            if (result.isSuccessful && result.body() != null) {
                _confirmResult.value = Result.success("Booking confirmed successfully")
            } else {
                _confirmResult.value = Result.failure(Exception(result.errorBody()?.string()))
            }
        } catch (e: Exception) {
            _confirmResult.value = Result.failure(e)
            Log.v(TAG, "Error confirming booking: ${e.message}")
        }
    }
}

//fun delete(bookingId: String) {
//    viewModelScope.launch {
//        try {
//            val result = repository.delete(bookingId)
//            if (result.isSuccessful && result.body() != null) {
//                _deleteResult.value = Result.success("Booking deleted successfully")
//            } else {
//                _deleteResult.value = Result.failure(Exception(result.errorBody()?.string()))
//            }
//        } catch (e: Exception) {
//            _deleteResult.value = Result.failure(e)
//            Log.v(TAG, "Error deleting booking: ${e.message}")
//        }
//    }
//}

fun deleteBooking(id: String) {
    viewModelScope.launch {
        try {
            val response = repository.deleteBooking(id)
            if (response.isSuccessful) {
                _deleteResult.value = Result.success("Deleted successfully")
            } else {
                _deleteResult.value = Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            _deleteResult.value = Result.failure(e)
        }
    }
}

fun deleteUser(id: String) {
    viewModelScope.launch {
        try {
            val response = repository.deleteUser(id)
            if (response.isSuccessful) {
                _deleteUserResult.value = Result.success("User deleted successfully")
                getUser() // Refresh the user list after deletion
            } else {
                _deleteUserResult.value = Result.failure(Exception("Failed to delete user"))
            }
        } catch (e: Exception) {
            _deleteUserResult.value = Result.failure(e)
        }
    }
}

fun deleteCar(id: String) {
    viewModelScope.launch {
        try {
            val response = repository.deleteCar(id)
            if (response.isSuccessful) {
                _deleteCarResult.value = Result.success("Car deleted successfully")
                getCars() // Refresh the car list after deletion
            } else {
                _deleteCarResult.value = Result.failure(Exception("Failed to delete car"))
            }
        } catch (e: Exception) {
            _deleteCarResult.value = Result.failure(e)
        }
    }
}

fun uploadCar(context: Context, uri:Uri){
    viewModelScope.launch {
        try {
            val multiPart=createMultipartBody(context,uri)
            val response=repository.uploadCar(multiPart)
            if(response.isSuccessful && response.body()!=null){
                _uploadCar.value=Result.success(response.body()!!)
            }
            else{
                _uploadCar.value=Result.failure(Exception(response.errorBody()?.string()?:"Request Failed"))
            }
        }
        catch (e:Exception){
            _uploadCar.value=Result.failure(e)
        }
    }
}

    fun createMultipartBody(context: Context, uri: Uri, partName:String="image"):MultipartBody.Part?{
        return try{
            val inputStream=context.contentResolver.openInputStream(uri)  //The raw binary data
            val fileBytes=inputStream?.readBytes()
            val requestFile=fileBytes?.let{
                RequestBody.create("image/*".toMediaTypeOrNull(), it)
            }?: return null

            MultipartBody.Part.createFormData(
                partName,
                "image_${System.currentTimeMillis()}.jpg",
                requestFile
            )
        }
        catch(e:Exception){
            null
        }
    }

    fun createCar(car: CarPost) {
        viewModelScope.launch {
            try {
                val response = repository.createCar(car)
                if (response.isSuccessful && response.body() != null) {
                    _createCarResult.value = Result.success(response.body()!!)
                } else {
                    _createCarResult.value = Result.failure(Exception(response.errorBody()?.string() ?: "Failed to create car"))
                }
            } catch (e: Exception) {
                _createCarResult.value = Result.failure(e)
                Log.v(TAG, "Error creating car: ${e.message}")
            }
        }
    }

    fun updateCar(id: String, car: CarPost) {
        viewModelScope.launch {
            try {
                val response = repository.updateCar(id, car)
                if (response.isSuccessful && response.body() != null) {
                    _updateCarResult.value = Result.success(response.body()!!)
                } else {
                    _updateCarResult.value = Result.failure(Exception(response.errorBody()?.string() ?: "Failed to update car"))
                }
            } catch (e: Exception) {
                _updateCarResult.value = Result.failure(e)
                Log.v(TAG, "Error updating car: ${e.message}")
            }
        }
    }
}