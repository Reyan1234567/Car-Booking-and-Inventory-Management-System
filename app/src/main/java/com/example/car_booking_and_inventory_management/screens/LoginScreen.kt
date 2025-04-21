package com.example.frontend.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontend.DataStore.TokenManager
import com.example.frontend.R
import com.example.frontend.data.LoginInput
import com.example.frontend.repositories.authRepository
import com.example.frontend.ui.theme.Vold
import com.example.frontend.viewModelFactory.AuthViewModelFactory
import com.example.frontend.viewModels.AuthViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    repository: authRepository,
    navController: NavController,
    tokenManager: TokenManager
) {
    val factory=AuthViewModelFactory(repository,tokenManager)
    val authViewModel: AuthViewModel = viewModel(factory=factory)
    val loginState=authViewModel.loginResult.collectAsState()

    val context= LocalContext.current

    var username by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}

    var passwordVisible by remember { mutableStateOf(false) }

    var isLoading = authViewModel.isLoading.collectAsState()

    val snackbarHostState=remember{ SnackbarHostState() }
    val scope= rememberCoroutineScope()

    LaunchedEffect(loginState.value) {
        loginState.value?.onSuccess {loginData->
            val accessToken=loginData.accessToken
            val refreshToken=loginData.refreshToken
            Toast.makeText(context, "Login successful! Welcome ${username}", Toast.LENGTH_LONG).show()
        }?.onFailure {
            Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_LONG).show()
        }
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
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Login", fontSize = 20.sp, style = TextStyle(fontFamily = Vold))
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = username,
                    onValueChange = { username = it },
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
                        .border(1.dp,Color.LightGray, RoundedCornerShape(16.dp))
                        ,
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
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
                    }                    ,trailingIcon = {IconButton(
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
                        .border(1.dp,Color.LightGray, RoundedCornerShape(16.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    ClickableText(text = AnnotatedString(text = "Forgot Password?"),
                        style = TextStyle(color = Color.Blue, fontSize = 16.sp),
                        onClick = {}
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val user = LoginInput(username = username, password = password)
                        authViewModel.login(user)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEA6307),
                        contentColor = Color.White
                    )
                ) {
                    if (isLoading.value) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        Text("Sign-in?")
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

















