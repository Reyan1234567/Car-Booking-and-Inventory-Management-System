package com.example.car_booking_and_inventory_management.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.BookingResponse
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel

@Composable
fun BookingHistoryScreen(viewModel:CarFilterViewModel) {
    val historyResult= viewModel.historyResponse.collectAsState().value
    var historyArray by remember { mutableStateOf(emptyList<BookingResponse>()) }
    var context= LocalContext.current
    LaunchedEffect(Unit) {
        val id= viewModel.getuserId()
        val result= viewModel.getHistory(id!!)
    }

    LaunchedEffect(historyResult){
        historyResult?.onSuccess {
            historyArray=it
        }?.onFailure {
            Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Booking History", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            items(historyArray) { history ->
                BookingCard(booking = history)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun BookingCard(booking: BookingResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFA057), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Car ID: ${booking.carId}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Status: ${booking.bookingStatus}", fontSize = 14.sp, color = Color.White)
            Text("Start: ${booking.startDate}  End: ${booking.endDate}", fontSize = 14.sp, color = Color.White)
            Text("Pickup: ${booking.pickupLocationName}", fontSize = 14.sp, color = Color.White)
            Text("Dropoff: ${booking.dropoffLocationName}", fontSize = 14.sp, color = Color.White)
            Text("Price: ${booking.estimatedTotalPrice ?: "N/A"}", fontSize = 14.sp, color = Color.White)
            if (booking.remark.isNotEmpty()) {
                Text("Remark: ${booking.remark}", fontSize = 14.sp, color = Color.White)
            }
        }
    }
}
