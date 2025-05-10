package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.TableRow
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.BookingCarUser
import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(modifier: Modifier = Modifier, viewModel:AdminViewModel, navController: NavController) {
    var snackbarHostState = SnackbarHostState()
    var listOfBookings by remember { mutableStateOf(emptyList<BookingCarUser>()) }
    var message by remember { mutableStateOf("") }

    var result = viewModel.BookingsResponse
    LaunchedEffect(result.value) {
        val Result = result.value
        Result?.onSuccess {
            listOfBookings = it
            Log.v(TAG,it.toString())
        }?.onFailure {
            message="No Booking Present"
            Log.v(TAG, it.toString())
            snackbarHostState.showSnackbar("${it}")
            Log.v(TAG,it.toString())
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getBookings()
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(" ") },
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
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEA6307),
                    titleContentColor = Color(0xFFEA6307)
                ),
            )
        },
        bottomBar = {
            BottomNavbar2(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Booking", style = TextStyle(fontSize = 30.sp, fontFamily = Vold))
                Spacer(modifier = Modifier.padding(5.dp))
                        Column(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())){
                            BookingTableHeader()
                                if (listOfBookings.isEmpty()) {
                                    Text(text=message, style = TextStyle(fontFamily = Vold, fontSize = 24.sp), textAlign = TextAlign.Center)
                                } else {
                                    listOfBookings.mapIndexed { index, booking ->
                                        BookingTableRow(booking, onEditClick = {
                                        navController.navigate("bookingDetail/${booking._id}")
                                        })
                                    } }
                }
            }
        }
    }
}

    @Composable
    fun BookingTableHeader(modifier: Modifier = Modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(8.dp)
        ) {
            Text("Start Date", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("End Date", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("Pickup", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("Dropoff", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("Pickup Time", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("Dropoff Time", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("Status", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("Plate", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("User", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
            Text("Edit", fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp))
        }
    }


    @Composable
    fun BookingTableRow(
        booking: BookingCarUser,
        modifier: Modifier = Modifier,
        onEditClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(booking.startDate, modifier.width(120.dp))
            Text(booking.endDate, modifier.width(120.dp))
            Text(booking.pickupLocationName, modifier.width(120.dp))
            Text(booking.dropoffLocationName, modifier.width(120.dp))
            Text(booking.pickupTime, modifier.width(120.dp))
            Text(booking.dropoffTime, modifier.width(120.dp))
            Text(booking.bookingStatus, modifier.width(120.dp))
            Text(booking.carPlate, modifier.width(120.dp))
            Text(booking.username, modifier.width(120.dp))
            IconButton(onClick = onEditClick, modifier = Modifier.background(Color.Yellow)) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = ""
                )
            }
        }
    }





