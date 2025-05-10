package com.example.car_booking_and_inventory_management.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.ui.theme.Vold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditCreate(
    modifier: Modifier = Modifier,
    navController: NavController,
    onSubmit: (Car) -> Unit
) {
    var plate by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var make by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var transmissionType by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var passengerCapacity by remember { mutableStateOf("") }
    var luggageCapacity by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var dailyRate by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Car", style = MaterialTheme.typography.titleLarge.copy(fontFamily = Vold)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            @Composable
            fun inputField(label: String, value: String, onChange: (String) -> Unit) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onChange,
                    label = { Text(label) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            inputField("Plate", plate) { plate = it }
            inputField("Name", name) { name = it }
            inputField("Make", make) { make = it }
            inputField("Price", price) { price = it }
            inputField("Model", model) { model = it }
            inputField("Year", year) { year = it }
            inputField("Category", category) { category = it }
            inputField("Type", type) { type = it }
            inputField("Transmission Type", transmissionType) { transmissionType = it }
            inputField("Fuel Type", fuelType) { fuelType = it }
            inputField("Passenger Capacity", passengerCapacity) { passengerCapacity = it }
            inputField("Luggage Capacity", luggageCapacity) { luggageCapacity = it }
            inputField("Image URL", imageUrl) { imageUrl = it }
            inputField("Daily Rate", dailyRate) { dailyRate = it }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    onSubmit(
                        Car(
                            plate = plate,
                            name = name,
                            make = make,
                            price = price.toIntOrNull() ?: 0,
                            model = model,
                            year = year.toIntOrNull() ?: 2000,
                            category = category,
                            type = type,
                            transmissionType = transmissionType,
                            fuelType = fuelType,
                            passengerCapacity = passengerCapacity.toIntOrNull() ?: 5,
                            luggageCapacity = luggageCapacity,
                            imageUrl = imageUrl,
                            dailyRate = dailyRate.toIntOrNull() ?: 50
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}
