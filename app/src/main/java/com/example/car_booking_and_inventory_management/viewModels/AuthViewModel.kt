package com.example.car_booking_and_inventory_management.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_booking_and_inventory_management.data.LoginInput
import com.example.car_booking_and_inventory_management.data.LoginResult
import com.example.car_booking_and_inventory_management.data.ProfilePageResult
import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.data.UploadResponse
import com.example.car_booking_and_inventory_management.data.accountEdit
import com.example.car_booking_and_inventory_management.data.saveResponse
import com.example.car_booking_and_inventory_management.repositories.authRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.Result
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: authRepository): ViewModel(){
    data class ProfileState(
        val id:String="",
        val username:String="",
        val phoneNumber:String="",
        val email:String="",
        val profileImageUrl:String?=null,
        val licenseImageUrl:String?=null,
        val profileUri:Uri?=null,
        val licenseUri:Uri?=null,
        val isLoading:Boolean=false,
        val error:String?=null,
        val isntBackable:Boolean=false
    )

    var role by  mutableStateOf("")

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> =_state.asStateFlow()

    private val _loginResult= MutableStateFlow<Result<LoginResult>?>(null)
    val loginResult: StateFlow<Result<LoginResult>?> = _loginResult.asStateFlow()

    private val _signupResponse=MutableStateFlow<Result<Signup>?>(null)
    val signupResponse:StateFlow<Result<Signup>?> =_signupResponse.asStateFlow()

    private val _accessTokenResponse= MutableStateFlow<Result<LoginResult>?>(null)
    val accessTokenResponse:StateFlow<Result<LoginResult>?> =_accessTokenResponse.asStateFlow()

    private val _saveResult=MutableStateFlow<Result<saveResponse>?>(null)
    val saveResult:StateFlow<Result<saveResponse>?> = _saveResult.asStateFlow()

//    private val _profileUploadResult=MutableStateFlow<Result<UploadResponse>?>(null)
//    val profileUploadResult:StateFlow<Result<UploadResponse>?> = _profileUploadResult.asStateFlow()
//
//    private val _licenseUploadResult=MutableStateFlow<Result<UploadResponse>?>(null)
//    val licenseUploadResult:StateFlow<Result<UploadResponse>?> = _licenseUploadResult.asStateFlow()
//
//    private val _updateResponse=MutableStateFlow<Result<ProfilePageResult>?>(null)
//    val updateResponse:StateFlow<Result<ProfilePageResult>?> =_updateResponse.asStateFlow()



    var isLoading1 = MutableStateFlow(false)
    var isLoading3=MutableStateFlow(false)
////////////////////////////////////////////////////////////////////
    var firstname by mutableStateOf("")
    var id by mutableStateOf("")
    var lastname by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
/////////////////////////////////////////////////////
    var profilePhoto by mutableStateOf("")
    var licensePhoto by mutableStateOf("")
///////////////////////////////////////////////////
    var isLoading2 =MutableStateFlow(false)
 ////////////////////////////////////////////////////
            init {
                loadInitialDate()
            }

            private fun loadInitialDate(){
                viewModelScope.launch {
                    _state.value=_state.value.copy(
                        id=repository.getId() ?: "",
                        username=repository.getUsername() ?: "",
                        phoneNumber=repository.getPhoneNumber() ?: "",
                        email=repository.getEmail() ?: "",
                        profileImageUrl=repository.getProfilePhoto(),
                        licenseImageUrl=repository.getLicensePhoto()
                    )
                }
            }

            fun updateField(field:String, value:String){
                _state.value=_state.value.copy(isntBackable=true)
                _state.value = when(field){
                    "username"->
                        _state.value.copy(username=value)
                    "phoneNumber"->_state.value.copy(phoneNumber=value)
                    "email"->_state.value.copy(email=value)
                    "profileUri"->_state.value.copy(profileUri=Uri.parse(value))
                    "licenseUri"->_state.value.copy(licenseUri=Uri.parse(value))
                    else->_state.value
                }

            }

            fun updateState(field:String,value:Uri?){
                _state.value=_state.value.copy(isntBackable=true)
                _state.value = when(field){
                    "profileUri"->_state.value.copy(profileUri=value)
                    "licenseUri"->_state.value.copy(licenseUri=value)
                    else->_state.value
                }
            }

            suspend fun uploadProfile(context:Context):UploadResponse{
                Log.v(TAG, "Inside the uploadProfile function")
                val uri = _state.value.profileUri ?: run {
                    _state.value = _state.value.copy(error = "No image selected")
                    return UploadResponse()
                }
                    _state.value=_state.value.copy(isLoading=true)
                    try{
                        val multipart = createMultipartBody(context, uri) ?: run {
                            _state.value = _state.value.copy(error = "Failed to create upload data")
                            return UploadResponse()
                        }
                        val result= repository.uploadProfile(multipart)
                        if(result.isSuccessful){
                            _state.value=_state.value.copy(
                                profileImageUrl=result.body()?.url,
                                profileUri=null
                            )
                            return result.body()?: UploadResponse()
                        }
                        else{
                            _state.value=_state.value.copy(
                                error=result.errorBody()?.string(),
                                profileUri=null
                            )
                            return UploadResponse()
                        }
                    }
                    finally {
                        _state.value=_state.value.copy(isLoading=false)
                    }

            }

    suspend fun uploadLicense(context:Context):UploadResponse{
        Log.v(TAG, "Inside the uploadLicense function")
        val uri = _state.value.licenseUri ?: run {
            _state.value = _state.value.copy(error = "No image selected")
            return UploadResponse()
        }
        _state.value=_state.value.copy(isLoading=true)
        try{
            val multipart = createMultipartBody(context, uri) ?: run {
                _state.value = _state.value.copy(error = "Failed to create upload data")
                return UploadResponse()
            }
            Log.v(TAG,multipart.toString())
            val result= repository.uploadLicense(multipart)
            if(result.isSuccessful){
                _state.value=_state.value.copy(
                    licenseImageUrl =result.body()?.url,
                    licenseUri =null
                )
                return result.body()?: UploadResponse()
            }
            else{
                _state.value=_state.value.copy(
                    error=result.errorBody()?.string(),
                    licenseUri =null
                )
                return UploadResponse()
            }
        }
        finally {
            _state.value=_state.value.copy(isLoading=false)
        }
    }
        fun updateBackable(state:Boolean){
            _state.value=_state.value.copy(isntBackable=state)
        }

            suspend fun saveProfile(profileId:String?,licenseId:String?){
                _state.value=_state.value.copy(isLoading = true)
                _state.value=_state.value.copy(isntBackable=true)
                try{
                   val result=repository.editAccount(
                        id=repository.getId() ?: "",
                        body=accountEdit(
                            username = _state.value.username,
                            email = _state.value.email,
                            phoneNumber = _state.value.phoneNumber,
                            profilePhoto = profileId,
                            licensePhoto = licenseId
                        )
                    )
                    if(result.isSuccessful && result.body()!=null){
                        _saveResult.value=Result.success(result.body()!!)
                    }
                    else{
                        _saveResult.value=Result.failure(Exception(result.errorBody()?.string()))
                    }
                    repository.editFromSave(_state.value.username, _state.value.email, _state.value.phoneNumber, _state.value.profileImageUrl, _state.value.licenseImageUrl)

                }
                catch (e:Exception){
                    _saveResult.value=Result.failure(e)
                    _state.value=_state.value.copy(
                        error = e.message)
                }
                finally {
                  _state.value=_state.value.copy(isLoading = false)
                  _state.value=_state.value.copy(isntBackable=false)
                }
            }

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
                                    id=it.user.id,
                                    accessToken = it.accessToken,
                                    refreshToken =it.refreshToken,
                                    username = it.user.username,
                                    email = it.user.email,
                                    phoneNumber = it.user.phoneNumber,
                                    profilePhoto = it.user.profilePhoto,
                                    licensePhoto = it.user.licensePhoto,
                                    firstName=it.user.firstName,
                                    lastName=it.user.lastName,
                                    role=it.user.role
                                )
                                role=repository.getRole()?:"user"
                                Log.v(TAG,"from the viewModel's save function $role")
                                Log.v(TAG,"from the viewModel's save function ${it.user.username}")
                            }

                        }
                        else{
                            _loginResult.value=Result.failure(Exception(response.errorBody()?.string()))
                            Log.v(TAG,"${ response.errorBody()?.string()}")
                        }
                    }
                    catch(e:Exception){
                        _loginResult.value=Result.failure(Exception("Network error"))
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

    fun checkAccessToken(){
        viewModelScope.launch {
            try {
                val result=repository.checkAT()
                if(result.isSuccessful && result.body()!=null){
                    _accessTokenResponse.value=Result.success(result.body()!!)
                }
                else{
                    _accessTokenResponse.value=Result.failure(Exception(result.errorBody()?.string()))
                }
            }
            catch (e:Exception){
                _accessTokenResponse.value=Result.failure(e)
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

//    fun uploadProfile(context:Context, uri: Uri, partName:String="image" ){
//        viewModelScope.launch {
//            isLoading3.value=true
//            val multipart=createMultipartBody(context,uri)
//            if(multipart!=null){
//                try {
//                   val response=repository.uploadProfile(multipart)
//                    if(response.isSuccessful && response.body()!=null){
//                        _profileUploadResult.value= Result.success(response.body()!!)
//                    }
//                    else{
//                        _profileUploadResult.value= Result.failure(Exception(response.errorBody()?.string()))
//                    }
//                }
//                catch (e:Exception){
//                    _profileUploadResult.value= Result.failure(e)
//                }
//                finally {
//                    isLoading3.value=false
//                }
//            }
//        }
//    }

//    fun uploadLicense(context:Context, uri: Uri, partName:String="image" ){
//        viewModelScope.launch {
//            isLoading3.value=true
//            val multipart=createMultipartBody(context,uri)
//            if(multipart!=null){
//                try {
//                    val response=repository.uploadLicense(multipart)
//                    if(response.isSuccessful && response.body()!=null){
//                        _licenseUploadResult.value= Result.success(response.body()!!)
//                    }
//                    else{
//                        _licenseUploadResult.value= Result.failure(Exception(response.errorBody()?.string()))
//                    }
//                }
//                catch (e:Exception){
//                    _licenseUploadResult.value= Result.failure(e)
//                }
//                finally {
//                    isLoading3.value=false
//                }
//            }
//        }
//    }

//    suspend fun getProfilePhoto():String?{
//        Log.v(TAG, "getProfile from the viewModel's get function ${repository.getProfilePhoto()}")
//       return repository.getProfilePhoto()
//    }
//
//    suspend fun getLicensePhoto():String?{
//        Log.v(TAG, "getLicense from the viewModel's get function ${repository.getLicensePhoto()}")
//        return repository.getLicensePhoto()
//    }
//
//    suspend fun getUsername():String?{
//        Log.v(TAG, "getUsername from the viewModel's get function ${repository.getUsername()}")
//        return repository.getUsername()
//    }
//
//    suspend fun getEmail(): String? {
//        Log.v(TAG, "getEmail from the viewModel's get function ${repository.getEmail()}")
//        return repository.getEmail()
//    }
//
//    suspend fun getPhoneNumber(): String? {
//        Log.v(TAG, "getPhoneNumber from the viewModel's get function ${repository.getPhoneNumber()}")
//        return repository.getPhoneNumber()
//    }
//
//    suspend fun getUserId(): String? {
//        Log.v(TAG, "getUserId from the viewModel's get function ${repository.getId()}")
//        return repository.getId()
//    }

//    suspend fun editAccount(id:String, body: accountEdit) {
//        viewModelScope.launch {
//            try {
//                val result = repository.editAccount(id, body)
//                if (result.isSuccessful && result.body() != null) {
//                    _updateResponse.value = Result.success(result.body()!!)
                    // Update local state
//                    username = body.username
//                    email = body.email
//                    phoneNumber = body.phoneNumber
//                    profilePhoto = body.profilePhoto
//                    licensePhoto = body.licensePhoto
//                } else {
//                    _updateResponse.value = Result.failure(Exception(result.errorBody()?.string() ?: "Update failed"))
//                }
//            } catch (e: Exception) {
//                _updateResponse.value = Result.failure(e)
//            }
//        }
//    }

    suspend fun logout(){
        repository.logout()
    }

    suspend fun getRole():String?{
        return repository.getRole()
    }

}