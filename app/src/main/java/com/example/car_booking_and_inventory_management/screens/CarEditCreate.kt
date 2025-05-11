package com.example.car_booking_and_inventory_management.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarPost
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditCreate(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var plate by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var make by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var passengerCapacity by remember { mutableIntStateOf(0) }
    var luggageCapacity by remember { mutableIntStateOf(0) }
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    var dailyRate by remember { mutableStateOf("") }

    // Error states
    var plateError by remember { mutableStateOf<String?>(null) }
    var nameError by remember { mutableStateOf<String?>(null) }
    var makeError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }
    var modelError by remember { mutableStateOf<String?>(null) }
    var yearError by remember { mutableStateOf<String?>(null) }
    var dailyRateError by remember { mutableStateOf<String?>(null) }
    var imageError by remember { mutableStateOf<String?>(null) }

    var CategoryItems = listOf("Economy", "Business", "MiniBus", "Truck")
    var selectedOptionText by remember { mutableStateOf(CategoryItems[0]) }

    var TransmissionItems = listOf("Automatic", "Manual")
    var selectedTransmission by remember { mutableStateOf(TransmissionItems[0]) }

    var FuelTypes = listOf("Benzene", "Petrol", "JetFuel")
    var selectedFuel by remember { mutableStateOf(FuelTypes[0]) }

    val carLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
        imageError = null
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add New Car",
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = Vold)
                    )
                },
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
            OutlinedTextField(
                value = plate,
                onValueChange = { 
                    plate = it
                    plateError = null
                },
                label = { Text("Plate") },
                isError = plateError != null,
                supportingText = { plateError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { 
                    name = it
                    nameError = null
                },
                label = { Text("Name") },
                isError = nameError != null,
                supportingText = { nameError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = make,
                onValueChange = { 
                    make = it
                    makeError = null
                },
                label = { Text("Make") },
                isError = makeError != null,
                supportingText = { makeError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = price,
                onValueChange = { 
                    price = it
                    priceError = null
                },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = priceError != null,
                supportingText = { priceError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = model,
                onValueChange = { 
                    model = it
                    modelError = null
                },
                label = { Text("Model") },
                isError = modelError != null,
                supportingText = { modelError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = year,
                onValueChange = { 
                    year = it
                    yearError = null
                },
                label = { Text("Year") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = yearError != null,
                supportingText = { yearError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )

            ExpandableText(
                label = "Vehicle Type",
                selectedOption = selectedOptionText,
                options = CategoryItems
            ) { selectedOptionText = it }

            ExpandableText(
                label = "Transmission Type",
                selectedOption = selectedTransmission,
                options = TransmissionItems
            ) { selectedTransmission = it }

            ExpandableText(
                label = "Fuel Type",
                selectedOption = selectedFuel,
                options = FuelTypes
            ) { selectedFuel = it }

            NumBox(
                minVal = 0,
                maxVal = 10,
                label = "Passenger Capacity",
                value = passengerCapacity,
                onValueChange = { passengerCapacity = it }
            )

            NumBox(
                minVal = 0,
                maxVal = 10,
                label = "Luggage Capacity",
                value = luggageCapacity,
                onValueChange = { luggageCapacity = it }
            )

            OutlinedTextField(
                value = imageUrl?.toString() ?: "No image selected",
                onValueChange = {},
                label = { Text("Image") },
                readOnly = true,
                isError = imageError != null,
                supportingText = { imageError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        carLauncher.launch("image/*")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Select Image")
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = dailyRate,
                onValueChange = { 
                    dailyRate = it
                    dailyRateError = null
                },
                label = { Text("Daily Rate") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = dailyRateError != null,
                supportingText = { dailyRateError?.let { Text(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val validationResult = validateFields(
                        plate = plate,
                        name = name,
                        make = make,
                        price = price,
                        model = model,
                        year = year,
                        dailyRate = dailyRate,
                        imageUrl = imageUrl
                    )
                    
                    if (validationResult.isValid) {
                        // Create CarPost object and proceed with submission
                        CarPost(
                            plate = plate,
                            name = name,
                            make = make,
                            price = price.toIntOrNull() ?: 0,
                            model = model,
                            year = year.toIntOrNull() ?: 2000,
                            category = selectedOptionText,
                            type = selectedOptionText,
                            transmissionType = selectedTransmission,
                            fuelType = selectedFuel,
                            passengerCapacity = passengerCapacity,
                            luggageCapacity = luggageCapacity,
                            imageUrl = imageUrl,
                            dailyRate = dailyRate.toIntOrNull() ?: 50
                        )
                    } else {
                        // Update error states
                        plateError = validationResult.plateError
                        nameError = validationResult.nameError
                        makeError = validationResult.makeError
                        priceError = validationResult.priceError
                        modelError = validationResult.modelError
                        yearError = validationResult.yearError
                        dailyRateError = validationResult.dailyRateError
                        imageError = validationResult.imageError
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Submit", color = Color.White)
            }
        }
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val plateError: String? = null,
    val nameError: String? = null,
    val makeError: String? = null,
    val priceError: String? = null,
    val modelError: String? = null,
    val yearError: String? = null,
    val dailyRateError: String? = null,
    val imageError: String? = null
)

private fun validateFields(
    plate: String,
    name: String,
    make: String,
    price: String,
    model: String,
    year: String,
    dailyRate: String,
    imageUrl: Uri?
): ValidationResult {
    var isValid = true
    val plateError = if (plate.isBlank()) "Plate number is required" else null
    val nameError = if (name.isBlank()) "Name is required" else null
    val makeError = if (make.isBlank()) "Make is required" else null
    val priceError = when {
        price.isBlank() -> "Price is required"
        price.toIntOrNull() == null -> "Price must be a valid number"
        price.toInt() <= 0 -> "Price must be greater than 0"
        else -> null
    }
    val modelError = if (model.isBlank()) "Model is required" else null
    val yearError = when {
        year.isBlank() -> "Year is required"
        year.toIntOrNull() == null -> "Year must be a valid number"
        year.toInt() < 1900 || year.toInt() > 2024 -> "Year must be between 1900 and 2024"
        else -> null
    }
    val dailyRateError = when {
        dailyRate.isBlank() -> "Daily rate is required"
        dailyRate.toIntOrNull() == null -> "Daily rate must be a valid number"
        dailyRate.toInt() <= 0 -> "Daily rate must be greater than 0"
        else -> null
    }
    val imageError = if (imageUrl == null) "Image is required" else null

    isValid = plateError == null && nameError == null && makeError == null && 
              priceError == null && modelError == null && yearError == null && 
              dailyRateError == null && imageError == null

    return ValidationResult(
        isValid = isValid,
        plateError = plateError,
        nameError = nameError,
        makeError = makeError,
        priceError = priceError,
        modelError = modelError,
        yearError = yearError,
        dailyRateError = dailyRateError,
        imageError = imageError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableText(modifier: Modifier = Modifier,label:String,selectedOption:String, options:List<String>, onOptionSelected:(String)->Unit ) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectedOption ->
                DropdownMenuItem(
                    text = { Text(selectedOption) },
                    onClick = {
                         onOptionSelected(selectedOption)
                        expanded = false
                    }
                )
            }

        }
    }
}

@Composable
fun NumBox(
    modifier: Modifier = Modifier,
    minVal: Int,
    maxVal: Int,
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = { },
        readOnly = true,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {
                        if (value < maxVal) {
                            onValueChange(value + 1)
                        }
                    },
                    enabled = value < maxVal
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Increase"
                    )
                }
                IconButton(
                    onClick = {
                        if (value > minVal) {
                            onValueChange(value - 1)
                        }
                    },
                    enabled = value > minVal
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Decrease"
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp)
    )
}