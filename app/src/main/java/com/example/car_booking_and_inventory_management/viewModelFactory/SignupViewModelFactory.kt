package com.example.car_booking_and_inventory_management.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.car_booking_and_inventory_management.viewModels.SignupViewModel
import com.example.frontend.repositories.authRepository

class SignupViewModelFactory (private val repository: authRepository):ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(SignupViewModel::class.java)){
            @Suppress("UNCHEKED_CAST")
            return SignupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:${modelClass.name}")
    }

}