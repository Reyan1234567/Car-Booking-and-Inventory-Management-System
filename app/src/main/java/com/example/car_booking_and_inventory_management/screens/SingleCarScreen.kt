package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.BookingRequest
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.ui.theme.Inter
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.times

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SingleCarScreen(modifier: Modifier = Modifier,navController: NavController,viewModel: CarFilterViewModel, id:String) {

    val legitmacyCheck=viewModel.legitmacyResponse.collectAsState()
    val legit=legitmacyCheck.value

    val snackbarHostState= remember { SnackbarHostState() }
    val viewModelResult=viewModel.carsFilterResponse.collectAsState()
    var theInfoIwantInAnArray by remember { mutableStateOf(emptyList<CarResponse>()) }
    var theInfoIwant by remember { mutableStateOf(CarResponse(
        null,
        plate = null,
        name = null,
        make = null,
        price = null,
        model = null,
        year = null,
        category = null,
        type = null,
        transmissionType = null,
        fuelType = null,
        passengerCapacity = null,
        luggageCapacity = null,
        imageUrl = null,
        dailyRate = null
    )) }

    var bookingResult=viewModel.bookingCreationResponse.collectAsState()

    viewModelResult.value?.onSuccess{info->
        Log.v(TAG, "the info is $info")
        theInfoIwantInAnArray=info.filter{
        it._id==id
        }
        theInfoIwant=theInfoIwantInAnArray[0]
    }?.onFailure {

    }
    var sdf=SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var difference=sdf.parse(viewModel.endDate)?.time?.minus(sdf.parse(viewModel.startDate)?.time!!);
    var dateFormat= difference?.div((8600000))
    var EstimatedPrice= dateFormat?.let { theInfoIwant.dailyRate?.times(it) }

    LaunchedEffect(legit) {
        legit?.onSuccess{
            var booking= BookingRequest(
                userId=viewModel.getuserId().toString(),
                carId=id,
                startDate = viewModel.startDate,
                endDate = viewModel.endDate,
                pickupTime = viewModel.startTime,
                dropoffTime = viewModel.endTime,
                pickupLocationName = viewModel.pickUp,
                dropoffLocationName = viewModel.dropOff,
                estimatedTotalPrice = EstimatedPrice?.toDouble()?:0.0
            )
            viewModel.createBooking(booking)
        }?.onFailure {
            navController.navigate("profile")
            snackbarHostState.showSnackbar(it.message.toString())
        }
    }

    LaunchedEffect(bookingResult.value) {
        bookingResult.value?.onSuccess {
            snackbarHostState.showSnackbar("Your Booking is Pending, please check the booking history for any update")
        }?.onFailure {
            snackbarHostState.showSnackbar("Some error happened while trying to create a bookin,${it.message}")
        }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ){
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = rememberAsyncImagePainter(theInfoIwant.imageUrl),
                    contentDescription = "the image before",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .height(300.dp)
                )
                Spacer(modifier.padding(8.dp))
                theInfoIwant.name?.let { it1 ->
                    Text(
                        text = it1,
                        style = TextStyle(fontFamily = Vold, fontSize = 20.sp),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Specifications",
                            style = TextStyle(fontFamily = Vold, fontSize = 16.sp),
                            modifier = Modifier.padding(4.dp)
                        )
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column {
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "person",
                                        modifier = Modifier.height(16.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(theInfoIwant.year.toString())
                                }

                                Row {
                                    Icon(
                                        imageVector = Icons.Default.MailOutline,
                                        contentDescription = "person",
                                        modifier = Modifier.height(16.dp)

                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(theInfoIwant.passengerCapacity.toString())
                                }
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                            Column {
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "person",
                                        modifier = Modifier.height(16.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    theInfoIwant?.luggageCapacity?.let { it1 -> Text(it1) }
                                }

                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Build,
                                        contentDescription = "person",
                                        modifier = Modifier.height(16.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    theInfoIwant.category?.let{Text(it)}
                                }
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                            Column() {
                                Text("vehicle Type", style = TextStyle(fontFamily = Vold))
                                theInfoIwant.type?.let{Text(it)}
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                            Column() {
                                Text("Fuel Type", style = TextStyle(fontFamily = Vold))
                                theInfoIwant.fuelType?.let{Text(it)}
                            }
                        }
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Price per day", style = TextStyle(fontFamily = Vold))
                    Text("1500 ETB", style = TextStyle(fontFamily = Inter))
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEC7320),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Price per day", style = TextStyle(fontFamily = Vold))
                                Text("1500 ETB", style = TextStyle(fontFamily = Inter))
                            }
                            Column {
                                Text("Price per day", style = TextStyle(fontFamily = Vold))
                                Text("1500 ETB", style = TextStyle(fontFamily = Inter))
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Price per day", style = TextStyle(fontFamily = Vold))
                                Text("1500 ETB", style = TextStyle(fontFamily = Inter))
                            }
                            Column {
                                Text("Price per day", style = TextStyle(fontFamily = Vold))
                                Text("1500 ETB", style = TextStyle(fontFamily = Inter))
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Price per day", style = TextStyle(fontFamily = Vold))
                        Text("1500 ETB", style = TextStyle(fontFamily = Inter))
                    }
                }
                Button(
                    onClick = {
                        var username= runBlocking{ (viewModel.getUsername()) }
                        if(username!=null){
                            val valid=viewModel.checkLegitimacy(username = username)
                        }
                        else{
                            runBlocking{ snackbarHostState.showSnackbar("You need to login first") }
                        }
                    },
                    modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEA6307)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Book now")
                }

            }

            IconButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier.padding(16.dp)) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "back icon",
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        ),
                )
            }
        }
    }
}


