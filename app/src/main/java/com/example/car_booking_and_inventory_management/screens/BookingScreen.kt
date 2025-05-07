package com.example.car_booking_and_inventory_management.screens

import android.widget.TableRow
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.ui.theme.Vold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Booking(modifier: Modifier = Modifier) {
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(" ") },
                modifier = modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Image(
                            painter= painterResource(R.drawable.polo),
                            contentDescription =""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription =""
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
            BottomNavbar2(navController = rememberNavController())
        }
    ){ innerPadding->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()){
            Column(modifier=Modifier.padding(20.dp)){
                Text("Booking", style= TextStyle(fontSize = 30.sp, fontFamily = Vold))
                Spacer(modifier=Modifier.padding(5.dp))
                LazyRow(){
                    item{ TableHeader() }

//                    BookingList.mapIndexed{index, booking->
//                        TableRow(booking)
//                    }
                }
            }
        }
    }
}



@Composable
fun TableHeader(modifier: Modifier = Modifier) {
    Row(modifier=Modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(8.dp)){
            Text("ID", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
            Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
            Text("Grade", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
            Text("Score", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun TableRow(booking: BookingTable, modifier: Modifier = Modifier) {
    Row(modifier=Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp)){
        Text(booking.startDate,modifier.weight(0.1f))
        Text(booking.endDate,modifier.weight(0.1f))
        Text(booking.pickupLocationName,modifier.weight(0.1f))
        Text(booking.dropoffLocationName,modifier.weight(0.1f))
        Text(booking.pickupTime,modifier.weight(0.1f))
        Text(booking.dropoffTime,modifier.weight(0.1f))
        Text(booking.bookingStatus,modifier.weight(0.1f))
        Text(booking.carPlate,modifier.weight(0.1f))
        Text(booking.username,modifier.weight(0.1f))
        IconButton(onClick = {}, modifier=Modifier.background(Color.Yellow)){
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription=""
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun Ccc() {
    Booking()
}