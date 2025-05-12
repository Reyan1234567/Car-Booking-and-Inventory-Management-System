package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.BookingCarUser
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun BookingDetailScreen(modifier: Modifier, navController: NavController, viewModel: AdminViewModel, id:String) {
//    val bookingResult=viewModel.BookingsResponse.collectAsState().value
//    var bks by remember { mutableStateOf(emptyList<BookingCarUser>())}
//    var booking by remember { mutableStateOf<BookingCarUser>(null) }
//    LaunchedEffect(Unit) {
//        bookingResult?.onSuccess {
//            bks=it
//            booking=bks.first{bk->
//                bk._id==id
//            }
//        }
//    }
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title={Text("")},
//                modifier=Modifier.fillMaxWidth(),
//                navigationIcon = {
//                    IconButton(onClick={navController.popBackStack()}){
//                        Icon(
//                            imageVector = Icons.Default.KeyboardArrowLeft,
//                            contentDescription =""
//                        )
//                    }
//                },
//                actions={
//                    IconButton(onClick={}){
//                        Icon(
//                            imageVector = Icons.Default.AccountCircle,
//                            contentDescription =""
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFFEA6307),
//                    titleContentColor = Color.Transparent
//                )
//            )
//        }
//    ){innerPadding->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Spacer(modifier = Modifier.height(16.dp))
//
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Image(
//                    painter = rememberAsyncImagePainter(booking.carImage.url),
//                    contentDescription = "Car",
//                    modifier = Modifier
//                        .size(140.dp)
//                        .background(Color.White, shape = RoundedCornerShape(7.dp))
//                )
//                Image(
//                    painter = rememberAsyncImagePainter(booking.userImage.url),
//                    contentDescription = "User",
//                    modifier = Modifier
//                        .size(140.dp)
//                        .background(Color.White, shape = RoundedCornerShape(7.dp))
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "BookingId-1",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//
//
//            Divider(
//                color = Color.LightGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 12.dp)
//            )
//
//            DetailRow("Booking Id", "Customer Id", "bookingid-1", "customerid-1")
//            DetailRow("Pickup Date", "Vehicle Id", "2025-04-15", "vehicleid-1")
//            DetailRow("Pickup Location", "Pickup Time", "Downtown Addis Ababa", "2:30 PM")
//            DetailRow("Drop off Date", "Drop off Time", "2025-04-15", "2:30 PM")
//            DetailSingle("Drop off Location", "Downtown Addis Ababa Branch")
//
//
//            DetailRow("Total Price", "Payment Status", "150 ETB", "Pending")
//
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(text = "Status", fontSize = 14.sp, color = Color.Gray)
//                    Text(text = "Pending", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                }
//                Spacer(modifier = Modifier.width(40.dp))
//                Column(
//                    modifier = Modifier.weight(1f),
//                    horizontalAlignment = Alignment.End
//                ) {
//                    Text(text = "Approval", fontSize = 14.sp, color = Color.Gray)
//                    ActionButton("Approve", Color(0xFF4CAF50)) // Green
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                ActionButton("Edit", Color(0xFFFFC107))     // Yellow
//                ActionButton("Delete", Color(0xFFFF4B3E))   // Red
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//        }
//    }
//}

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
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label1,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = Vold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Text(
                text = value1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = Vold,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        Spacer(modifier = Modifier.width(32.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = label2,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = Vold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Text(
                text = value2,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = Vold,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
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
fun ActionButton(text: String, color: Color, onClick:()->Unit) {
    Button(
    onClick = { },
    colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
    Text(text = text, color = Color.White)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookingDetailScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: AdminViewModel,
    id: String
) {
    val context= LocalContext.current
    val bookingResult = viewModel.BookingsResponse.collectAsState().value
    var bks by remember { mutableStateOf(emptyList<BookingCarUser>()) }
    var booking by remember { mutableStateOf<BookingCarUser?>(null) }

    var clicked by remember { mutableStateOf(false) }

    val cancel=viewModel.cancelResult.collectAsState().value
    val confirm=viewModel.confirmResult.collectAsState().value
    val delete=viewModel.deleteResult.collectAsState().value

    LaunchedEffect(Unit) {
        Log.v(TAG, "in the Booking Detail")
        bookingResult?.onSuccess {
            bks = it
            booking = bks.firstOrNull { bk -> bk._id == id }
        }
    }

    LaunchedEffect(cancel) {
        cancel?.onSuccess {
            clicked=true
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }?.onFailure {
            clicked=false
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(confirm) {
        confirm?.onSuccess {
            clicked=true
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }?.onFailure {
            clicked=false
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(delete) {
        delete?.onSuccess {
            clicked=true
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }?.onFailure {
            clicked=false
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
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
                    titleContentColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            booking?.let { booking ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Log.v(TAG, booking.carImages.toString())
                    if(booking.carImages?.url != null){
                        Log.v(TAG, booking.carImages.url)
                        Image(
                            painter = rememberAsyncImagePainter(booking.carImages.url),
                            contentDescription = "Car",
                            modifier = Modifier
                                .size(140.dp)
                                .background(Color.White, shape = RoundedCornerShape(7.dp))
                        )
                    }
                    else{
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Car",
                            modifier = Modifier
                                .size(140.dp)
                                .background(Color.White, shape = RoundedCornerShape(7.dp))
                        )
                    }
                    if(booking.profilePhotos?.url != null){
                        Log.v(TAG, booking.profilePhotos.url)
                        Image(
                            painter = rememberAsyncImagePainter(booking.profilePhotos.url),
                            contentDescription = "User",
                            modifier = Modifier
                                .size(140.dp)
                                .background(Color.White, shape = RoundedCornerShape(7.dp))
                        )
                    }
                    else {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "User",
                            modifier = Modifier
                                .size(140.dp)
                                .background(Color.White, shape = RoundedCornerShape(7.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Booking ID
                Text(
                    text = "Booking ID: ${booking._id}",
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

                // Booking Details
                DetailRow("Booking ID", "Username", booking._id, booking.username?:"")
                DetailRow("Pickup Date", "Dropoff Date", booking.startDate?:"", booking.endDate?:"")
                DetailRow("Pickup Time", "Dropoff Time", booking.pickupTime?:"", booking.dropoffTime?:"")
                DetailRow("Pickup Location", "Dropoff Location", booking.pickupLocationName?:"", booking.dropoffLocationName?:"")
                DetailRow("Total Price", "Payment Status", "${booking.estimatedTotalPrice} ETB", booking.bookingStatus?:"")

                Spacer(modifier = Modifier.height(24.dp))

                // Status and Approval
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Status", fontSize = 14.sp, color = Color.Gray)
                        Text(text = booking.bookingStatus?:"", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Edit and Delete Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if(!clicked) {
                        Button(
                            onClick = {
                                viewModel.confirm(id)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFC107),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text("Approve")
                        }
                        Button(
                            onClick = {
                                viewModel.cancel(id)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF4B3E),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text("Cancel")
                        }
                    }
                    else{
                        Text(text="is Loading ....", style= TextStyle(fontSize = 20.sp, fontFamily= Vold))
                    }
                    Button(
                        onClick={
                            viewModel.deleteBooking(id)
                        }, 
                        modifier=Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE36300),
                            contentColor = Color.White,
                        )
                    ){
                        Text("Delete", style=TextStyle(fontFamily = Vold, fontSize = 16.sp))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            } ?: run {
                // Show a loading or error message if booking is null
                Text(
                    text = "Loading booking details...",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}




