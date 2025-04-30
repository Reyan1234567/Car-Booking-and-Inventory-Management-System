package com.example.car_booking_and_inventory_management.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.viewModels.SignupViewModel
//import com.example.frontend.R
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup1(modifier: Modifier = Modifier,navController: NavController, viewModel: AuthViewModel) {
    var firstname by remember { mutableStateOf("") }
    var firstnameErr by remember { mutableStateOf("") }

    var lastname by remember { mutableStateOf("") }
    var lastnameErr by remember { mutableStateOf("") }

    var phoneNumber by remember { mutableStateOf("") }
    var phoneNumberErr by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var emailErr by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    val birthDate= selectedDateMillis?.let{ formatDate(it) }?:""
    var birthDateErr by remember { mutableStateOf("") }

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
            .verticalScroll(rememberScrollState())
        , horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.polo),
            contentDescription = "Logo",
            modifier=Modifier
                .width(100.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Spacer(modifier=Modifier.padding(12.dp))
        Text("Signup", fontFamily= Vold, fontSize=20.sp)
        Spacer(modifier=Modifier.padding(12.dp))
        Column(){
            Text("Firstname",modifier=Modifier.padding(start=12.dp))
            TextField(
                value =firstname,
                onValueChange = {
                    firstname=it
                    firstnameErr=validateName(it) },
                placeholder = {Text("John", style=TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D)))},
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                colors=TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White
                )
            )
            Text(firstnameErr,modifier=Modifier.padding(start=12.dp), color = Color.Red)
        }

        Spacer(modifier=Modifier.padding(4.dp))
        Column(){
            Text("Lastname",modifier=Modifier.padding(start=12.dp))
            TextField(
                value =lastname,
                onValueChange = {
                    lastname=it
                    lastnameErr= validateName(it)
                },
                placeholder = {Text("Doe" , style=TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D)))},
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                colors=TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White
                )
            )
            Text(lastnameErr,modifier=Modifier.padding(start=12.dp), color = Color.Red)
        }

        Spacer(modifier=Modifier.padding(4.dp))
        Column(){
            Text("Birth-Date",modifier=Modifier.padding(start=12.dp))
            TextField(
                value =birthDate,
                onValueChange = {
                   birthDateErr=validateBirthDate(birthDate)
                    Log.v(TAG, "BIRTHDATEERROR:${birthDateErr}, and ${birthDate}")
                },
                placeholder = {Text("Enter your Birth-Date" , style=TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D)))},
                readOnly = true,
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePicker=true
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calender button"
                        )
                    }
                },
                colors=TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White
                )
            )
            Text(birthDateErr,modifier=Modifier.padding(start=12.dp), color = Color.Red)
        }

        Spacer(modifier=Modifier.padding(4.dp))
        Column(){
            Text("Phone-Number",modifier=Modifier.padding(start=12.dp))
            TextField(
                value =phoneNumber,
                onValueChange = {
                    phoneNumber=it
                    phoneNumberErr= validatePhone(it)
                                },
                placeholder = {Text("09********", style=TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D)))},
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                colors=TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White
            ))
            Text(phoneNumberErr,modifier=Modifier.padding(start=12.dp), color = Color.Red)
        }

        Spacer(modifier=Modifier.padding(4.dp))
        Column(){
            Text("Email",modifier=Modifier.padding(start=12.dp))
            TextField(
                value =email,
                onValueChange = {
                    email=it
                    emailErr= validateEmail(it)
                },
                placeholder = {Text("JohnDoe@gmail.com", style=TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D)))},
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                colors=TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White
                )
            )
            Text(emailErr,modifier=Modifier.padding(start=12.dp), color = Color.Red)
        }

        Spacer(modifier=Modifier.padding(12.dp))
        Button(
            onClick = {
                viewModel.updateEmail(email)
                viewModel.updateLastname(lastname)
                viewModel.updateFirstname(firstname)
                viewModel.updateBirthDate(birthDate)
                viewModel.updatePhoneNumber(phoneNumber)
                navController.navigate("sign_up2")
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEA6307),
                contentColor=Color.White
            ),
            enabled = if(firstnameErr=="" && lastnameErr=="" && phoneNumberErr=="" && birthDateErr=="" && emailErr=="" && firstname!="" && lastname!=""&& phoneNumber!="" && birthDate!=""&& email!=""){
                true
            }
            else{
                false
            }
        ) {
            Text("Next")
        }

        if(showDatePicker){
            DatePickerModal(
                onDateSelected = {
                    selectedDateMillis=it
                },
                onDismiss = {showDatePicker=false}
            )
        }

    }
}

fun validateName(name:String):String{
    if(name.isBlank()){
        return "This field can't be Blank"
    }
    else if(name.length<2){
        return "Enter atleast 3 characters"
    }
    else if(name.length>20){
        return "Enter a shorter name"
    }
    else{
       return ""
    }
}

fun validatePhone(phoneNumber:String):String{
    return (if(phoneNumber.isBlank()){
        "This field is required"
    }

    else if(phoneNumber.length!=10){
        "Invalid phoneNumber, try again"
    }

    else if(phoneNumber[0]!='0' || phoneNumber[1]!='9'){
        "You must start with 09"
    }
    else{
        ""
    })
}

fun validateEmail(email:String):String{
    return (if(email.isBlank()){
        "Email is required"
    }
    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        "Incorrect email format"
    }
    else{
        ""
    })
}


fun validateBirthDate(birthDate:String):String{
    val sdf=SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
    val BirthDate=sdf.parse(birthDate)!!
    val startDate="01/01/1950"
    val endDate="01/01/2010"

    val StartDate=sdf.parse(startDate)!!
    val EndDate=sdf.parse(endDate)!!

    return (if(birthDate.isBlank()){
        "This Field can't be empty"
    }
    else if(BirthDate.before(StartDate)){
        "Enter your real age"
    }
    else if(BirthDate.after(EndDate)){
        "You must be 18 to signup, but just set it to 2007"
    }
    else{
        ""
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState() // the date the user selected in the ui

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis) // the date the user selected in the ui
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)  // connects the ui and the
    }
}

fun formatDate(timestamp:Long):String{
    val sdf=SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

//ui-->DatePicker()-->datePickerState