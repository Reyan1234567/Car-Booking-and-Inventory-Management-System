package com.example.car_booking_and_inventory_management.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.data.Signup
import com.example.car_booking_and_inventory_management.viewModelFactory.SignupViewModelFactory
import com.example.car_booking_and_inventory_management.viewModels.SignupViewModel
import com.example.frontend.R
import com.example.frontend.repositories.authRepository
import com.example.frontend.ui.theme.Vold
import com.example.frontend.viewModelFactory.AuthViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(modifier: Modifier = Modifier, repository:authRepository, navController: NavController) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }

    val factory= SignupViewModelFactory(
        repository
    )
    val context = LocalContext.current
    val SignupViewModel:SignupViewModel=viewModel(factory=factory)
    var signupResponse=SignupViewModel.signupResponse.collectAsState()

    LaunchedEffect(signupResponse.value) {
        signupResponse.value?.onSuccess{
            Toast.makeText(context, "Account created!", Toast.LENGTH_LONG).show()
            navController.navigate("")
        }?.onFailure{
            Toast.makeText(context,"Error:${it.message}", Toast.LENGTH_LONG).show()
        }

    }
    @Composable
    fun styledTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) = BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .fillMaxWidth()
    )

    @Composable
    fun longerStyledTextField(
        value:String,
        onValueChange:(String)->Unit,
        modifier: Modifier
    )= BasicTextField(
        value=value,
        onValueChange=onValueChange,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color.White)
            .padding(horizontal = 8.dp, vertical = 10.dp).fillMaxWidth()

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFF8633)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.polo),
            contentDescription = "logo",
            modifier = Modifier
                .width(120.dp)
                .clip(RoundedCornerShape(32.dp))
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Sign-up", style = TextStyle(fontFamily=Vold, fontSize = 20.sp))
        Spacer(modifier = Modifier.padding(16.dp))

        // First row
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.height(60.dp).weight(1f)) {
                Text("Firstname", fontSize = 16.sp,)
                Spacer(modifier = Modifier.padding(2.dp))
                styledTextField(value = firstName, onValueChange = { firstName = it })
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Column(modifier = Modifier.height(60.dp).weight(1f)) {
                Text("Lastname", fontSize = 16.sp)
                Spacer(modifier = Modifier.padding(2.dp))
                styledTextField(value = lastName, onValueChange = { lastName = it })
            }
        }

        // Second row
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.height(60.dp).weight(1f)) {
                Text("Birth-date", fontSize = 16.sp)
                Spacer(modifier = Modifier.padding(2.dp))
                styledTextField(value = birthDate, onValueChange = { birthDate = it })
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Column(modifier = Modifier.height(60.dp).weight(1f)) {
                Text("Phone Number", fontSize = 16.sp)
                Spacer(modifier = Modifier.padding(2.dp))
                styledTextField(value = phoneNumber, onValueChange = { phoneNumber = it })
            }
        }

            Column(modifier=Modifier.height(60.dp).padding(horizontal = 16.dp)){
                Text("Email", fontSize = 16.sp)
                Spacer(modifier=Modifier.padding(2.dp))
                longerStyledTextField(
                    value = email, onValueChange = { email = it },Modifier
                )
            }
        Spacer(modifier = Modifier.padding(8.dp))

        Column(modifier=Modifier.height(60.dp).padding(horizontal = 16.dp)){
            Text("Username", fontSize = 16.sp)
            Spacer(modifier=Modifier.padding(2.dp))
            longerStyledTextField(
                value = username, onValueChange = { username = it },Modifier
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))

        Column(modifier=Modifier.height(60.dp).padding(horizontal = 16.dp)){
            Text("Enter password", fontSize = 16.sp)
            Spacer(modifier=Modifier.padding(2.dp))
            longerStyledTextField(
                value = password, onValueChange = { password = it },Modifier
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))

        Column(modifier=Modifier.height(60.dp).padding(horizontal = 16.dp)){
            Text("Re-Enter password", fontSize = 16.sp)
            Spacer(modifier=Modifier.padding(2.dp))
            longerStyledTextField(
                value = rePassword, onValueChange = { rePassword = it },Modifier
            )
        }
        Spacer(modifier = Modifier.padding(32.dp))

        Button(
            onClick = {
                val signup= Signup(firstName,lastName,phoneNumber,birthDate,email,username,password,rePassword)
                SignupViewModel.signup(signup)

            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEA6307),
                contentColor = Color.White,
            ),
        ) {
            Text("Sign-up", fontSize = 16.sp)
        }
    }
}
