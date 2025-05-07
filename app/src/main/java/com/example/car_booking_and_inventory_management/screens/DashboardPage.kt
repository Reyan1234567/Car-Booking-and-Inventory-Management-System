package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.room.parser.Section
import coil.compose.rememberImagePainter
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.ui.theme.Vold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFEA6307),
                    titleContentColor = Color.White
                ),
                title = {
                    Text(" ")
                },
                modifier = modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {}){
                        Image(
                            painter = painterResource(R.drawable.polo),
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = ""
                        )
                    }
                },
            )
        },
        bottomBar = { BottomNavbar2(navController = rememberNavController()) }
    ){
        Column(
            modifier = Modifier.padding(8.dp)
        ){
            Text("Dashboard",style= TextStyle(
                fontFamily = Vold,
                fontSize = 24.sp
                    )
            )
            Row(modifier=Modifier.fillMaxWidth()){
                Box(){}
                Box(){}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Dash() {
    Dashboard()
}