//package com.example.car_booking_and_inventory_management.data
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//
//data class SignupFormState (
//var username by remember mutableStateOf("")
//var usernameErr by mutableStateOf("")
//
//var phoneNumber by mutableStateOf("")
//var phoneNumberErr by mutableStateOf("")
//
//var birthDate by mutableStateOf("")
//var birthDateErr by mutableStateOf("")
//
//var email by mutableStateOf("")
//var emailErr by mutableStateOf("")
//
//var password by mutableStateOf("")
//var passwordErr by mutableStateOf("")
//
//var rePassword by mutableStateOf("")
//var rePasswordErr by mutableStateOf("")
//
//fun validate(): Boolean {
//    val isUsernameValid = username.isNotEmpty() && usernameErr.isEmpty()
//    val isPhoneNumberValid = phoneNumber.isNotEmpty() && phoneNumberErr.isEmpty()
//    val isBirthDateValid = birthDate.isNotEmpty() && birthDateErr.isEmpty()
//    val isEmailValid = email.isNotEmpty() && emailErr.isEmpty()
//    val isPasswordValid = password.isNotEmpty() && passwordErr.isEmpty()
//    val isRePasswordValid = rePassword.isNotEmpty() && rePasswordErr.isEmpty()
//
//    val isFormValid = isUsernameValid && isPhoneNumberValid && isBirthDateValid &&
//            isEmailValid && isPasswordValid && isRePasswordValid
//
//    println("Validation Results (Form State):")
//    println("  Username: Valid = $isUsernameValid, Value = '$username', Error = '$usernameErr'")
//    println("  Phone Number: Valid = $isPhoneNumberValid, Value = '$phoneNumber', Error = '$phoneNumberErr'")
//    println("  Birth Date: Valid = $isBirthDateValid, Value = '$birthDate', Error = '$birthDateErr'")
//    println("  Email: Valid = $isEmailValid, Value = '$email', Error = '$emailErr'")
//    println("  Password: Valid = $isPasswordValid, Value = '******', Error = '$passwordErr'")
//    println("  Re-enter Password: Valid = $isRePasswordValid, Value = '******', Error = '$rePasswordErr'")
//    println("Form is valid: $isFormValid")
//
//    return isFormValid
//)
