package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.car_booking_and_inventory_management.data.CarCI
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CarDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AdminViewModel,
    id: String
) {
    val context = LocalContext.current
    val carResult = viewModel.CarsResponse.collectAsState().value
    var cars by remember { mutableStateOf(emptyList<CarCI>()) }
    var car by remember { mutableStateOf<CarCI?>(null) }

    LaunchedEffect(Unit) {
        carResult?.onSuccess {
            cars = it
            car = cars.firstOrNull { cr -> cr._id == id }
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

            car?.let { car ->
                // Car Image
                if(car.CI?.url != null){
                    Image(
                        painter = rememberAsyncImagePainter(car.CI.url),
                        contentDescription = "Car",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
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

                Spacer(modifier = Modifier.height(8.dp))

                // Car ID
                Text(
                    text = "Car ID: ${car._id}",
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

                // Car Details
                DetailRow("Plate", "Name", car.plate ?: "", car.name ?: "")
                DetailRow("Make", "Model", car.make ?: "", car.model ?: "")
                DetailRow("Year", "Category", car.year.toString(), car.category ?: "")
                DetailRow("Type", "Transmission", car.type ?: "", car.transmissionType ?: "")
                DetailRow("Fuel Type", "Passenger Capacity", car.fuelType ?: "", car.passengerCapacity.toString())
                DetailRow("Luggage Capacity", "Daily Rate", car.luggageCapacity ?: "", "${car.dailyRate} ETB")

                Spacer(modifier = Modifier.height(32.dp))
                Column{
                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE36300),
                            contentColor = Color.White,
                        ), shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Delete", style = TextStyle(fontFamily = Vold, fontSize = 16.sp))
                    }
                    Spacer(modifier=Modifier.padding(8.dp))
                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0C600),
                            contentColor = Color.White,
                        ), shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Edit", style = TextStyle(fontFamily = Vold, fontSize = 16.sp))
                    }
                }
            } ?: run {
                // Show a loading or error message if car is null
                Text(
                    text = "Loading car details...",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

