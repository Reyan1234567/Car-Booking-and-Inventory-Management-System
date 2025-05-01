package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import java.time.format.DateTimeFormatter
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
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
//import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarFilters
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.ui.theme.Inter
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CarSearchFilter(modifier: Modifier = Modifier, navController: NavController,viewModel:CarFilterViewModel) {
//    var differentDropOff by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var selectedStartTime by remember {mutableStateOf("")}
    var selectedEndTime by remember {mutableStateOf("")}

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    var differentDropOff=viewModel.showDropOff
    var showCalander by remember { mutableStateOf(false) }

    var selectedStartDateInMillis by remember { mutableStateOf<Long?>(null) }
    var selectedEndDateInMillis by remember { mutableStateOf<Long?>(null) }

    var FilterResult=viewModel.carsFilterResponse.collectAsState()
    var filterResult=FilterResult.value
    var isLoading=viewModel.isLoading

    val context= LocalContext.current
    val snackbarHostState= remember { SnackbarHostState() }

    LaunchedEffect(filterResult) {
        val result=filterResult
        result?.onSuccess {value->
            navController.navigate("searchResults")
            filterResult=null
            Log.v(TAG,"${value}")
        }?.onFailure {value->
            Log.v(TAG,"${value}")
            snackbarHostState.showSnackbar("${value.message}")
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                            viewModel.changeShowDropOff(false)
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
                            viewModel.changeShowDropOff(true)
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
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    showStartTimePicker = true
                                },
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
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    showEndTimePicker = true
                                },
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if(!differentDropOff){
                        viewModel.updateDropOff(viewModel.pickUp)
                            val filter= CarFilters(
                                viewModel.pickUp,
                                viewModel.dropOff,
                                viewModel.startDate,
                                viewModel.endDate,
                                viewModel.startTime,
                                viewModel.endTime
                            )
                            viewModel.getFilteredCars(filter)
                            Log.v(TAG,"${viewModel.pickUp},${viewModel.dropOff},${viewModel.startDate},${viewModel.endDate}")
                        }else{
                            val filter=CarFilters(
                                viewModel.pickUp,
                                viewModel.dropOff,
                                viewModel.startDate,
                                viewModel.endDate,
                                viewModel.startTime,
                                viewModel.endTime
                            )
                            viewModel.getFilteredCars(filter)
                            Log.v(TAG,"${viewModel.pickUp},${viewModel.dropOff},${viewModel.startDate},${viewModel.endDate}")
                        }
                              },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEC7320),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = if(viewModel.pickUp=="Click to select your destination"||viewModel.pickUp=="Click to select your destination"||viewModel.startDate=="Click to select Date"||viewModel.endDate=="Click to select Date"||viewModel.endTime=="Click to select time"||viewModel.startTime=="Click to select time"){
                        false
                    }
                    else{
                        true
                    }
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
    if(showStartTimePicker){
        TimePickerDialog(
            context,R.style.CustomTimePickerDialogTheme,{
                _,selectedHour:Int, selectedMinute:Int->
                selectedStartTime=String.format("%2d:%02d", selectedHour, selectedMinute)
                viewModel.startTime=selectedStartTime
                showStartTimePicker=false
            },
            hour,
            minute,
            false
        ).show()
    }

    if(showEndTimePicker){
        TimePickerDialog(
            context, R.style.CustomTimePickerDialogTheme,{
                    _,selectedHour:Int, selectedMinute:Int->
                selectedEndTime=String.format("%2d:%02d", selectedHour, selectedMinute)
                viewModel.endTime=selectedEndTime
                showEndTimePicker=false
            },
            hour,
            minute,
            false
        ).show()
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
            }
            ) {
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

