package com.example.car_booking_and_inventory_management.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.viewModels.SignupViewModel
//import com.example.frontend.R
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup2(modifier: Modifier = Modifier,navController: NavController, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var usernameErr by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordErr by remember { mutableStateOf("") }

    var rePassword by remember { mutableStateOf("") }
    var rePasswordErr by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var rePasswordVisible by remember { mutableStateOf(false) }

    var SignupState=viewModel.signupResponse.collectAsState()
    var isLoading=viewModel.isLoading2.collectAsState()

    val context= LocalContext.current
    val snackbarHostState= remember { SnackbarHostState() }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    Log.v(TAG,"${SignupState.value}")

    LaunchedEffect(SignupState.value) {
        Log.v(TAG,"verbose message: Signup Starting")
        Log.v(TAG, "SignupState is: ${SignupState.value}")
        val state = SignupState.value
        if (state != null) {
            if (state.isSuccess) {
                snackbarHostState.showSnackbar("Signup successful")
                Log.v(TAG, "Signup successful")
                navController.navigate("login") {
                    popUpTo("login") { inclusive = false }
                }
            } else {
                val error = state.exceptionOrNull()
                snackbarHostState.showSnackbar("Signup failed: ${error?.message}")
                Log.v(TAG, "Signup failed: ${error?.message}")
                navController.navigate("login")
            }
        }
        else{
            Log.v(TAG,"Shiii state is null")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ){padding->
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.polo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(100.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Text("Signup", fontFamily = Vold, fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(20.dp))
            Column() {
                Text("Username", modifier = Modifier.padding(start = 12.dp))
                TextField(
                    value = username,
                    onValueChange = {
                        username = it
                        usernameErr = validateUsername(it)
                    },
                    placeholder = {
                        Text(
                            "JohnDoe",
                            style = TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D))
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White
                    ),
                )
                Text(usernameErr, modifier = Modifier.padding(start = 12.dp), color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Column() {
                Text("Password", modifier = Modifier.padding(start = 12.dp))
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordErr = validatePassword(it)
                    },
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    placeholder = {
                        Text(
                            "*************",
                            style = TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D))
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White
                    ), trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            if (passwordVisible) {
                                Icon(
                                    painter = painterResource(R.drawable.eye),
                                    contentDescription = "eye",
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.eyeslash),
                                    contentDescription = "eye",
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                        }
                    }
                )

                Text(passwordErr, modifier = Modifier.padding(start = 12.dp), color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Column() {
                Text("Re-Enter your Password", modifier = Modifier.padding(start = 12.dp))
                TextField(
                    value = rePassword,
                    onValueChange = {
                        rePassword = it
                        rePasswordErr = validateRePassword(it, password)
                    },
                    placeholder = {
                        Text(
                            "*************",
                            style = TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D))
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White
                    ),
                    visualTransformation = if (rePasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { rePasswordVisible = !rePasswordVisible }
                        ) {
                            if (rePasswordVisible) {
                                Icon(
                                    painter = painterResource(R.drawable.eye),
                                    contentDescription = "eye",
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.eyeslash),
                                    contentDescription = "eye",
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                        }
                    }
                )
                Text(rePasswordErr, modifier = Modifier.padding(start = 12.dp), color = Color.Red)

            }

            Spacer(modifier = Modifier.padding(24.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val now: Date = Date()
                    viewModel.updateUsername(username)
                    viewModel.updatePassword(password)
                    Log.v(TAG, "button clicked")
                    Log.v(
                        TAG,
                        "${viewModel.firstname}, ${viewModel.lastname},${viewModel.phoneNumber},${viewModel.birthDate}, ${viewModel.email}, ${viewModel.username}, ${viewModel.password}"
                    )
                    val signupData = Signup(viewModel.firstname,
                        viewModel.lastname,
                        viewModel.phoneNumber,
                        viewModel.birthDate.let { sdf.parse(it) } ?: now,
                        viewModel.email,
                        viewModel.username,
                        viewModel.password)
                    viewModel.signup(signupData)
                    Log.v(TAG, "${SignupState.value}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEA6307),
                    contentColor = Color.White
                ),
                enabled = if (username != "" && password != "" && rePassword != "" && usernameErr == "" && passwordErr == "" && rePasswordErr == "") {
                    true
                } else {
                    false
                }
            ) {
                if (isLoading.value) {
                    Box(
                        modifier = Modifier.size(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text("Sign-up")
                }
            }


        }
    }
}

fun validatePassword(password:String):String {
    return (if (password.isBlank()) {
        "Password is required"
    } else if (password.length < 6) {
        "Password must be above 6 characters"
    } else if (password.length > 20) {
        "Password must be below 20 characters"
    } else {
        ""
    })
}

fun validateRePassword(rePassword:String,Password:String):String{
    return (if(rePassword.isBlank()){
        "You must re-Enter your Password"
    }
    else if(rePassword!=Password){
        "Enter the same Password, as the one above"
    }
    else{
        ""
    })
}

fun validateUsername(username:String):String {
    return (if (username.isBlank()) {
        "Username is required"
    } else if (username.length < 6) {
        "Username must be above 6 characters"
    }
    else {
        ""
    })

}


