package com.example.car_booking_and_inventory_management.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun BookingHistoryScreen() {
    val bookings = listOf(
        Booking("AUDI A8L", "Completed", "Jan 03, 2025", R.drawable.audi),
        Booking("Volkswagen ID B", "Cancelled", "Feb 03, 2025", R.drawable.volkswagen),
        Booking("AUDI A8L", "Completed", "Mar 03, 2025", R.drawable.audi),
        Booking("Toyota Corolla", "Pending", "Apr 03, 2025", R.drawable.corolla),
        Booking("Volkswagen GT", "Cancelled", "May 03, 2025", R.drawable.volkswagen),
        Booking("AUDI A8L", "Completed", "Jun 03, 2025", R.drawable.audi),
    )

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
            items(bookings) { booking ->
                BookingCard(booking = booking)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun BookingCard(booking: Booking) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFA057), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(booking.carName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(booking.status, fontSize = 14.sp, color = Color.White)
            Text("Date: ${booking.date}", fontSize = 14.sp, color = Color.White)
            Text("See More...", fontSize = 14.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = booking.imageResId),
            contentDescription = "Car Image",
            modifier = Modifier
                .size(80.dp)
        )
    }
}
