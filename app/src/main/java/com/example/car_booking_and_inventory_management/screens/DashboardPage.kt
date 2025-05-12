package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.parser.Section
import coil.compose.rememberImagePainter
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel
//import kotlinx.coroutines.flow.collectAsState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardPage(modifier: Modifier = Modifier, navController: NavController, viewModel: AdminViewModel) {
    val totalBookings = viewModel.TotalBookingsResponse.collectAsState().value
    val totalCars = viewModel.TotalCarsResponse.collectAsState().value
    val totalUsers = viewModel.TotalUsersResponse.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getTotalBookings()
        viewModel.getTotalCars()
        viewModel.getTotalUsers()
    }

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
                    IconButton(onClick = {}) {
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
        bottomBar = { BottomNavbar2(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxHeight()
        ) {
            Text(
                "Dashboard",
                style = TextStyle(
                    fontFamily = Vold,
                    fontSize = 30.sp
                ),
                modifier = Modifier.padding(20.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(modifier = Modifier.padding(20.dp)) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Total Bookings", style = TextStyle(fontSize = 20.sp, fontFamily = Vold))
                        Text(
                            totalBookings?.getOrNull()?.toString() ?: "0",
                            style = TextStyle(fontSize = 50.sp, fontFamily = Vold)
                        )
                    }
                }
                Card(modifier = Modifier.padding(20.dp)) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Total Cars", style = TextStyle(fontSize = 20.sp, fontFamily = Vold))
                        Text(
                            totalCars?.getOrNull()?.toString() ?: "0",
                            style = TextStyle(fontSize = 50.sp, fontFamily = Vold)
                        )
                    }
                }
            }
            Card(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Total Users", style = TextStyle(fontSize = 20.sp, fontFamily = Vold))
                        Text(
                            totalUsers?.getOrNull()?.toString() ?: "0",
                            style = TextStyle(fontSize = 50.sp, fontFamily = Vold)
                        )
                    }
                    Box(
                        modifier = Modifier.padding(vertical = 16.dp)
                            .height(80.dp)
                            .width(1.dp)
                            .background(Color.Black)
                    )
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Total Revenue", style = TextStyle(fontSize = 20.sp, fontFamily = Vold))
                        Text(
                            "₹0",
                            style = TextStyle(fontSize = 50.sp, fontFamily = Vold)
                        )
                    }
                }
            }
            Card(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Text(
                    "Revenue Chart",
                    style = TextStyle(fontFamily = Vold, fontSize = 16.sp),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

