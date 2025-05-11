package com.example.car_booking_and_inventory_management.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarPost
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarEditCreate(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AdminViewModel,
    carId: String = ""
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

    var CategoryItems = listOf("Economy", "Business", "MiniBus", "Truck")
    var selectedOptionText by remember { mutableStateOf(CategoryItems[0]) }

    var TransmissionItems = listOf("Automatic", "Manual")
    var selectedTransmission by remember { mutableStateOf(TransmissionItems[0]) }

    var FuelTypes = listOf("Benzene", "Petrol", "JetFuel")
    var selectedFuel by remember { mutableStateOf(FuelTypes[0]) }

    val uri = viewModel.uploadCar.collectAsState().value
    var url by remember { mutableStateOf("") }
    val context = LocalContext.current
    val carResult = viewModel.CarsResponse.collectAsState().value
    val updateResult = viewModel.updateCarResult.collectAsState().value

    // Load car data if in edit mode
    LaunchedEffect(carId) {
        if (carId.isNotEmpty()) {
            viewModel.getCars() // Ensure we have the latest car data
        }
    }

    LaunchedEffect(carId, carResult) {
        if (carId.isNotEmpty()) {
            carResult?.onSuccess { cars ->
                val car = cars.firstOrNull { it._id == carId }
                car?.let {
                    plate = it.plate ?: ""
                    name = it.name ?: ""
                    make = it.make ?: ""
                    price = it.price?.toString() ?: ""
                    model = it.model ?: ""
                    year = it.year?.toString() ?: ""
                    passengerCapacity = it.passengerCapacity ?: 0
                    luggageCapacity = it.luggageCapacity?.toIntOrNull() ?: 0
                    dailyRate = it.dailyRate?.toString() ?: ""
                    selectedOptionText = it.category ?: CategoryItems[0]
                    selectedTransmission = it.transmissionType ?: TransmissionItems[0]
                    selectedFuel = it.fuelType ?: FuelTypes[0]
                    url = it.CI?.url ?: ""
                }
            }
        }
    }

    // Handle update result
    LaunchedEffect(updateResult) {
        updateResult?.onSuccess {
            Toast.makeText(context, "Car updated successfully", Toast.LENGTH_SHORT).show()
            viewModel.getCars() // Refresh the car list
            navController.popBackStack()
        }?.onFailure {
            Toast.makeText(context, it.message ?: "Failed to update car", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uri) {
        val result = uri
        result?.onSuccess {
            url = it.id ?: ""
        }?.onFailure {
            url = ""
        }
    }

    val carLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (carId.isEmpty()) "Add New Car" else "Edit Car",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = Vold,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        "Basic Information",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

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
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Icon(Icons.Default.ArrowForward, contentDescription = null)
                        }
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
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Icon(Icons.Default.ArrowForward, contentDescription = null)
                        }
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
                        shape = RoundedCornerShape(12.dp),
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        "Specifications",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
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
                        shape = RoundedCornerShape(12.dp),

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
                        shape = RoundedCornerShape(12.dp),

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
                        shape = RoundedCornerShape(12.dp),

                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        "Details",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
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
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Icon(Icons.Default.ArrowForward, contentDescription = null)
                        }
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        "Image",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = imageUrl?.toString() ?: "No image selected",
                        onValueChange = {},
                        label = { Text("Image") },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        trailingIcon = {
                            IconButton(onClick = {
                                carLauncher.launch("image/*")
                            }) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Select Image",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (imageUrl != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "Selected car image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val validationResult = validateFields(
                        plate = plate,
                        name = name,
                        make = make,
                        price = price,
                        model = model,
                        year = year,
                        dailyRate = dailyRate
                    )
                    
                    if (validationResult.isValid) {
                        val car = CarPost(
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
                            imageUrl = url,
                            dailyRate = dailyRate.toIntOrNull() ?: 50
                        )

                        if (imageUrl != null) {
                            viewModel.uploadCar(context, imageUrl!!)
                        }

                        if (carId.isEmpty()) {
                            viewModel.createCar(car)
                        } else {
                            viewModel.updateCar(carId, car)
                        }
                    } else {
                        // Update error states
                        plateError = validationResult.plateError
                        nameError = validationResult.nameError
                        makeError = validationResult.makeError
                        priceError = validationResult.priceError
                        modelError = validationResult.modelError
                        yearError = validationResult.yearError
                        dailyRateError = validationResult.dailyRateError
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    if (carId.isEmpty()) "Create Car" else "Update Car",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
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
    val dailyRateError: String? = null
)

private fun validateFields(
    plate: String,
    name: String,
    make: String,
    price: String,
    model: String,
    year: String,
    dailyRate: String
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

    isValid = plateError == null && nameError == null && makeError == null && 
              priceError == null && modelError == null && yearError == null && 
              dailyRateError == null

    return ValidationResult(
        isValid = isValid,
        plateError = plateError,
        nameError = nameError,
        makeError = makeError,
        priceError = priceError,
        modelError = modelError,
        yearError = yearError,
        dailyRateError = dailyRateError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
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
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
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
                        contentDescription = "Increase",
                        tint = if (value < maxVal) MaterialTheme.colorScheme.primary else Color.Gray
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
                        contentDescription = "Decrease",
                        tint = if (value > minVal) MaterialTheme.colorScheme.primary else Color.Gray
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