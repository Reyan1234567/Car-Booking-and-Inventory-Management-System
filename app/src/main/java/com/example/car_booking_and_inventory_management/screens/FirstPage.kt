package com.example.car_booking_and_inventory_management.screens

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import java.time.format.TextStyle

@Composable
fun FirstPage(modifier: Modifier = Modifier, navController: NavController,viewModel: AuthViewModel) {
    val checkStatus= viewModel.accessTokenResponse.collectAsState()

    LaunchedEffect(checkStatus.value) {
        val result=checkStatus.value
        var role=viewModel.getRole()
        result?.onSuccess{
            if(role=="user"){
                Log.v(TAG,viewModel.role)
                navController.navigate("home")
            }
            else if(role=="admin"){
                Log.v(TAG,viewModel.role)
                navController.navigate("admin")
            }
            else{
                navController.navigate("login"){
                    popUpTo(route="login"){inclusive=false}
                }
            }
        }?.onFailure {
            navController.navigate("login"){
                popUpTo(route="login"){inclusive=false}
            }        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ){
            Image(
            painter = painterResource(id = R.drawable.polo),
            contentDescription = "Logo",
            modifier = Modifier
                .width(150.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit

        )
        Spacer(modifier=Modifier.padding(16.dp))
        Text(text="RENT THE CAR OF YOUR DREAMS", textAlign = TextAlign.Center, style = androidx.compose.ui.text.TextStyle(fontFamily = Vold, fontSize = 30.sp, fontStyle = FontStyle.Italic), modifier=Modifier.padding(8.dp))
        Spacer(modifier=Modifier.padding(16.dp))
        Image(
            painter = painterResource(R.drawable.carpic) ,
            contentDescription = "background pic",
            modifier = Modifier.height(350.dp)
        )
        Spacer(modifier=Modifier.padding(20.dp))
        Button(
            onClick={
                viewModel.checkAccessToken()
            }
            , modifier=Modifier.fillMaxWidth().padding(8.dp), colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEA6307)
            ), shape= RoundedCornerShape(12.dp)
        ){
            Text("Lets Go")
        }

    }
}

