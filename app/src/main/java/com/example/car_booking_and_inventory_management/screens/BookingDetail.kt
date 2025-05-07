package com.example.car_booking_and_inventory_management.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.car_booking_and_inventory_management.R

@Composable
fun BookingDetailScreen(onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Booking Details",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

//        Image(
//            painter = painterResource(id = R.drawable.audi),
//            contentDescription = "Car Image",
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            color = Color(0xFFE97400),
                    shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Toyota Camry", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Standard", fontSize = 14.sp, color = Color.White)

                Spacer(modifier = Modifier.height(8.dp))

                DetailItem("Pickup", "Addis Ababa\nFeb 22, 2025 at 1:00 AM")
                DetailItem("Drop off", "Debre Birhan\nFeb 22, 2025 at 2:00 PM\n4 hours")
                DetailItem("Booking ID", "pc-AT9877")
                DetailItem("Driver", "Included")
                DetailItem("Total cost", "52,000 ETB")
                DetailItem("Payment method", "Telebirr")
                DetailItem("Status", "Completed")
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
        Text(text = value, fontSize = 14.sp, color = Color.White)
    }
}
