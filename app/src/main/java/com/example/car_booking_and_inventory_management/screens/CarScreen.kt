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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarScreen(modifier: Modifier = Modifier, viewModel: AdminViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    var listOfCars by remember { mutableStateOf(listOf<Car>()) }

    val result = viewModel.CarsResponse.collectAsState()
    LaunchedEffect(result.value) {
        val Result = result.value
        Result?.onSuccess {
            listOfCars = it
        }?.onFailure {
            snackbarHostState.showSnackbar("${it}")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(" ") },
                modifier = modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(R.drawable.polo),
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
                    titleContentColor = Color(0xFFEA6307)
                ),
            )
        },
        bottomBar = {
            BottomNavbar2(navController = rememberNavController())
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Cars", style = TextStyle(fontSize = 30.sp, fontFamily = Vold))
                Spacer(modifier = Modifier.padding(5.dp))
                LazyRow {
                    item { CarsTableHeader() }
                    if (listOfCars.isEmpty()) {
                        item { Text("No Cars Present") }
                    } else {
                        items(listOfCars.size) { index ->
                            CarsTableRow(listOfCars[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarsTableHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Text("Plate", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
        Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
        Text("Make", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
        Text("Model", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
        Text("Year", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.1f))
        Text("Category", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
        Text("Type", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
        Text("Daily Rate", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.15f))
        Spacer(modifier = Modifier.weight(0.1f)) // for edit button
    }
}

@Composable
fun CarsTableRow(car: Car, modifier: Modifier = Modifier, onEditClick: () -> Unit = {}) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(car.plate, modifier = Modifier.weight(0.15f))
        Text(car.name, modifier = Modifier.weight(0.15f))
        Text(car.make, modifier = Modifier.weight(0.15f))
        Text(car.model, modifier = Modifier.weight(0.15f))
        Text(car.year.toString(), modifier = Modifier.weight(0.1f))
        Text(car.category, modifier = Modifier.weight(0.15f))
        Text(car.type, modifier = Modifier.weight(0.15f))
        Text(car.dailyRate.toString(), modifier = Modifier.weight(0.15f))
        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .weight(0.1f)
                .background(Color.Yellow)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit"
            )
        }
    }
} 