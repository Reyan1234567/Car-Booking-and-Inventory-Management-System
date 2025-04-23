//package com.example.frontend.viewModelFactory
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.car_booking_and_inventory_management.DataStore.TokenManager
//import com.example.car_booking_and_inventory_management.repositories.authRepository
//import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
//
//class AuthViewModelFactory(private val repository:authRepository, private val tokenManager: TokenManager):ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(AuthViewModel::class.java)) {
//            @Suppress("UNCHEKED_CAST")
//            return AuthViewModel(repository, tokenManager) as T
//        }
//           throw IllegalArgumentException("Unknown ViewModel class:${modelClass.name}")
//        }
//    }
