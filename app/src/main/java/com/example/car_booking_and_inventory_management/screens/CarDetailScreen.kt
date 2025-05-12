package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
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
    var clicked by remember { mutableStateOf(false) }
    val deleteCar = viewModel.deleteCarResult.collectAsState().value

    LaunchedEffect(Unit) {
        carResult?.onSuccess {
            cars = it
            car = cars.firstOrNull { cr -> cr._id == id }
        }
    }

    LaunchedEffect(deleteCar) {
        deleteCar?.onSuccess {
            clicked = true
            Toast.makeText(context, "Car deleted successfully", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }?.onFailure {
            clicked = false
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            Log.v(TAG, it.message.toString())
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Vehicle Details",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = Vold,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Account",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEA6307)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            car?.let { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Car Image
                        if(car.CI?.url != null) {
                            Image(
                                painter = rememberAsyncImagePainter(car.CI.url),
                                contentDescription = "Car",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "No Car Image",
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Car ID
                        Text(
                            text = "Car ID: ${car._id}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = Vold,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Basic Information",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = Vold,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        DetailRow("Plate", "Name", car.plate ?: "", car.name ?: "")
                        DetailRow("Make", "Model", car.make ?: "", car.model ?: "")
                        DetailRow("Year", "Category", car.year.toString(), car.category ?: "")
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Specifications",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = Vold,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        DetailRow("Type", "Transmission", car.type ?: "", car.transmissionType ?: "")
                        DetailRow("Fuel Type", "Passenger Capacity", car.fuelType ?: "", car.passengerCapacity.toString())
                        DetailRow("Luggage Capacity", "Daily Rate", car.luggageCapacity ?: "", "${car.dailyRate} ETB")
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Button(
                            onClick = { viewModel.deleteCar(id) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "Delete Car",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = Vold,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { navController.navigate("carEdit/${id}") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "Edit Car",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = Vold,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading car details...",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = Vold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}



