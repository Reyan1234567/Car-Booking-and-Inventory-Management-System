package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.LoginInput
//import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import com.example.car_booking_and_inventory_management.ui.theme.Vold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val loginState=viewModel.loginResult.collectAsState()

    val context= LocalContext.current

    var username by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}

    var usernameErr by remember {mutableStateOf("")}
    var passwordErr by remember {mutableStateOf("")}

    var passwordVisible by remember { mutableStateOf(false) }

    var isLoading = viewModel.isLoading1.collectAsState()

    val snackbarHostState=remember{ SnackbarHostState() }
    val scope= rememberCoroutineScope()

    LaunchedEffect(loginState.value) {

        loginState.value?.onSuccess {loginData->
            if(loginData.user.role=="admin"){
                navController.navigate("admin") {
                    popUpTo("login") { inclusive = true }
                }
            }
            else if(loginData.user.role=="user"){
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }

            snackbarHostState.showSnackbar("Login successful! Welcome ${loginData.user.firstName}")
        }?.onFailure {
            snackbarHostState.showSnackbar("Login failed: ${it.message}")        }
    }

    Scaffold(
        snackbarHost={SnackbarHost(hostState=snackbarHostState)}
    ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.polo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(200.dp)
                        .height(150.dp)
                        .padding(14.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Login", fontSize = 20.sp, style = TextStyle(fontFamily = Vold))
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = username,
                    onValueChange = {
                        username = it
                        usernameErr=validateFields(username)
                         },
                    placeholder = {
                        Text(
                            "Username",
                            style = TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D))
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                        ,
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White
                    )
                )
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                    Text(
                        text = usernameErr,
                        style = TextStyle(fontSize = 12.sp, color = Color.Red),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordErr=validateFields(password)},
                    visualTransformation = if(passwordVisible){
                        VisualTransformation.None
                    }
                    else{
                        PasswordVisualTransformation()
                    },
                    placeholder = {
                        Text(
                            text = "Password",
                            style = TextStyle(fontFamily = Vold, color = Color(0xFF9D9D9D))
                        )
                    },
                    trailingIcon = {IconButton(
                        onClick = {passwordVisible=!passwordVisible}
                    ) {
                        if(passwordVisible){
                            Icon(
                                painter = painterResource(R.drawable.eye),
                                contentDescription = "eye",
                                modifier=Modifier.size(20.dp)
                            )
                        }
                        else{
                            Icon(
                                painter = painterResource(R.drawable.eyeslash),
                                contentDescription = "eye",
                                modifier=Modifier.size(20.dp)
                            )
                        }

                    }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White
                    )
                )
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(
                        text = passwordErr,
                        style = TextStyle(fontSize = 12.sp, color = Color.Red),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    ClickableText(text = AnnotatedString(text = "Forgot Password?"),
                        style = TextStyle(color = Color(0xFF1E88E5), fontSize = 16.sp),
                        onClick = {}
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        if (username.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val user = LoginInput(username = username, password = password)
                        viewModel.login(user)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEA6307),
                        contentColor = Color.White
                    ),
                    enabled = if(usernameErr=="" && passwordErr=="" && username!="" && password!=""){
                        true
                    }
                    else{
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
                        Text("Sign-in")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text("Don't have an Account?")

                    ClickableText(
                        text = AnnotatedString(text = " Sign-up"),
                        style = TextStyle(color = Color.Blue, fontSize = 16.sp),
                        onClick = { navController.navigate("sign_up1") })
                }
            }

    }
}

fun validateFields(field:String):String{
    if(field.isBlank()){
        return "This field is required"
    }
    else{
        return ""
    }
}















