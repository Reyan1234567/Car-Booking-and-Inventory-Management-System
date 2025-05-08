package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookingDetailScreen(modifier: Modifier, navController: NavController, viewModel: AdminViewModel, id:String) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title={Text("")},
                modifier=Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick={navController.popBackStack()}){
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription =""
                        )
                    }
                },
                actions={
                    IconButton(onClick={}){
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription =""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEA6307),
                    titleContentColor = Color.Transparent
                )
            )
        }
    ){innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.audi),
                    contentDescription = "Car",
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White, shape = RoundedCornerShape(7.dp))
                )
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User",
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White, shape = RoundedCornerShape(7.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "BookingId-1",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )


            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            DetailRow("Booking Id", "Customer Id", "bookingid-1", "customerid-1")
            DetailRow("Pickup Date", "Vehicle Id", "2025-04-15", "vehicleid-1")
            DetailRow("Pickup Location", "Pickup Time", "Downtown Addis Ababa", "2:30 PM")
            DetailRow("Drop off Date", "Drop off Time", "2025-04-15", "2:30 PM")
            DetailSingle("Drop off Location", "Downtown Addis Ababa Branch")


            DetailRow("Total Price", "Payment Status", "150 ETB", "Pending")


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Status", fontSize = 14.sp, color = Color.Gray)
                    Text(text = "Pending", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(40.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "Approval", fontSize = 14.sp, color = Color.Gray)
                    ActionButton("Approve", Color(0xFF4CAF50)) // Green
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton("Edit", Color(0xFFFFC107))     // Yellow
                ActionButton("Delete", Color(0xFFFF4B3E))   // Red
            }

            Spacer(modifier = Modifier.height(32.dp))

        }
    }
}

@Composable
fun DetailRow(
label1: String,
label2: String,
value1: String,
value2: String
) {
Row(
modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 16.dp, vertical = 8.dp),
horizontalArrangement = Arrangement.SpaceBetween
) {
Column(modifier = Modifier.weight(1f)) {
    Text(text = label1, fontSize = 14.sp, color = Color.Gray)
    Text(text = value1, fontSize = 16.sp, fontWeight = FontWeight.Bold)
}
Spacer(modifier = Modifier.width(40.dp)) // Increased gap
Column(
    modifier = Modifier.weight(1f),
    horizontalAlignment = Alignment.End
) {
    Text(text = label2, fontSize = 14.sp, color = Color.Gray)
    Text(text = value2, fontSize = 16.sp, fontWeight = FontWeight.Bold)
}
}
}

@Composable
fun DetailSingle(label: String, value: String) {
Column(
modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 16.dp, vertical = 6.dp)
) {
Text(text = label, fontSize = 14.sp, color = Color.Gray)
Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
}
}

@Composable
fun ActionButton(text: String, color: Color) {
Button(
onClick = { },
colors = ButtonDefaults.buttonColors(containerColor = color)
) {
Text(text = text, color = Color.White)
}
}





