package com.example.car_booking_and_inventory_management.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.CarFilters
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.ui.theme.Inter
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CarSearchFilter(modifier: Modifier = Modifier, navController: NavController,viewModel:CarFilterViewModel) {
    var differentDropOff by remember { mutableStateOf(false) }
    var showCalander by remember { mutableStateOf(false) }

    var selectedStartDateInMillis by remember { mutableStateOf<Long?>(null) }
    var selectedEndDateInMillis by remember { mutableStateOf<Long?>(null) }

    var FilterResult=viewModel.carsFilterResponse.collectAsState()

    var isLoading=viewModel.isLoading

    val context= LocalContext.current
    val snackbarHostState= remember { SnackbarHostState() }

    LaunchedEffect(FilterResult.value) {
        val result=FilterResult.value
        result?.onSuccess {
            navController.navigate("")
        }?.onFailure {
            snackbarHostState.showSnackbar("Some Error happened")
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(modifier, navController)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.polo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .width(100.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Same drop-off",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = Vold,
                            color = if (!differentDropOff) Color.Black else Color.Gray
                        ),
                        modifier = Modifier.clickable {
                            differentDropOff = false
                        }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Different drop-off",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = Vold,
                            color = if (differentDropOff) Color.Black else Color.Gray
                        ),
                        modifier = Modifier.clickable {
                            differentDropOff = true
                        }
                    )
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEC7320)
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Column {
                        Text(
                            text = "Pick-up",
                            style = TextStyle(fontSize = 20.sp, color = Color.White, fontFamily = Inter),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = viewModel.pickUp,
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = Color.White,
                                fontFamily = Vold
                            ),
                            modifier = Modifier
                                .clickable { navController.navigate("searchPickups") }
                                .padding(16.dp)
                        )
                    }
                }
            }

            if (differentDropOff) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEC7320)
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(120.dp)
                    ) {
                        Column {
                            Text(
                                text = "Drop-off",
                                style = TextStyle(fontSize = 20.sp, color = Color.White, fontFamily = Inter),
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = viewModel.dropOff,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    fontFamily = Vold
                                ),
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("searchDropOffs")
                                    }
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEC7320)
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "From",
                            style = TextStyle(fontSize = 16.sp, color = Color.White, fontFamily = Inter),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = selectedStartDateInMillis?.let{formatDate(it)}?: viewModel.startDate,
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = Color.White,
                                fontFamily = Vold
                            ),
                            modifier = Modifier
                                .clickable {
                                    showCalander = true
                                }
                                .padding(16.dp)
                        )
                        Text(
                            text = viewModel.startTime,
                            style = TextStyle(fontSize = 16.sp, color = Color.Black, fontFamily = Vold),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "To",
                            style = TextStyle(fontSize = 16.sp, color = Color.White, fontFamily = Inter),
                            modifier = Modifier.padding(16.dp, top = 8.dp),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = selectedEndDateInMillis?.let{formatDate(it)}?: viewModel.startDate,
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = Color.White,
                                fontFamily = Vold
                            ),
                            modifier = Modifier
                                .clickable { navController.navigate("") }
                                .padding(16.dp)
                        )
                        Text(
                            text = viewModel.endTime,
                            style = TextStyle(fontSize = 16.sp, color = Color.Black, fontFamily = Vold),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
//                        navController.navigate("")
                        if(!differentDropOff){
                        viewModel.updateDropOff(viewModel.pickUp)
                            val filter=CarFilters(viewModel.pickUp,viewModel.dropOff,viewModel.startDate,viewModel.endDate)
                            viewModel.getFilteredCars(filter)
                        }else{
                            val filter=CarFilters(viewModel.pickUp,viewModel.dropOff,viewModel.startDate,viewModel.endDate)
                            viewModel.getFilteredCars(filter)
                        }
                              },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEC7320),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if(!isLoading){
                        Text(
                            text = "Search cars",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = Vold
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    else{
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
    if(showCalander){
        DateRangePickerModal(
            onDateRangeSelected = { selectedStartDateMillis, selectedEndDateMillis ->
                    selectedStartDateInMillis = selectedStartDateMillis
                    selectedEndDateInMillis = selectedEndDateMillis
                viewModel.updateStartDate(selectedStartDateInMillis?.let { formatDate(it) }?:viewModel.startDate)
                viewModel.updateEndDate(selectedEndDateInMillis?.let { formatDate(it) }?:viewModel.endDate)

            },
                    onDismiss = { showCalander = false }

        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (startDate: Long?, endDate: Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    val startInMillis = dateRangePickerState.selectedStartDateMillis
    val endInMillis=dateRangePickerState.selectedEndDateMillis

    val start=startInMillis?.let{Date(it)}
    val end= endInMillis?.let{ Date(it) }
    val Today= Date(System.currentTimeMillis())
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onDateRangeSelected(
                    dateRangePickerState.selectedStartDateMillis,
                    dateRangePickerState.selectedEndDateMillis
                )
                onDismiss()
            },
                enabled = if(start!=null && end!=null && start.before(end) && Today.before(start) ){
                    true
                }
            else{
                false
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(state = dateRangePickerState)
    }
}


