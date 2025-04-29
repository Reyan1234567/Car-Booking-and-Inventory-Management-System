package com.example.car_booking_and_inventory_management.screens

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
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.ui.theme.Inter
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun SingleCarScreen(modifier: Modifier = Modifier,navController: NavController,viewModel: CarFilterViewModel,name:String) {

    val legitmacyCheck=viewModel.legitimacyResponse.collectAsState()
    val legit=legitmacyCheck.value


    val viewModelResult=viewModel.carsFilterResponse.collectAsState()
    var theInfoIwantInAnArray by remember { mutableStateOf(emptyList<Car>()) }
    var theInfoIwant by remember { mutableStateOf(Car()) }
    viewModelResult.value?.onSuccess{info->
        theInfoIwantInAnArray=info.filter{
        it.name==name
        }
        theInfoIwant=theInfoIwantInAnArray[0]
    }?.onFailure {

    }

    LaunchedEffect(legit) {
//        if(legit!=null){
//            if(legit){
//                val
//                viewModel.createBooking()         }
//            else{
//                //navController and toast/snackBar
//            }
//
//        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceAround){
            Image(
                painter = rememberAsyncImagePainter(theInfoIwant.imageUrl),
                contentDescription = "the image before",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .height(300.dp)
            )
            Spacer(modifier.padding(8.dp))
            Text(text=theInfoIwant.name, style = TextStyle(fontFamily = Vold, fontSize = 20.sp), modifier=Modifier.padding(start=16.dp))
            Card(modifier=Modifier
                .fillMaxWidth()
                .padding(16.dp),
                colors=CardDefaults.cardColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape= RoundedCornerShape(8.dp)
            ){
                Column(modifier=Modifier
                    .padding(8.dp)
                    .fillMaxWidth()){
                    Text(text="Specifications", style = TextStyle(fontFamily = Vold, fontSize = 16.sp), modifier=Modifier.padding(4.dp))
                    Row(modifier=Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly){
                        Column{
                            Row{
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "person",
                                    modifier=Modifier.height(16.dp)
                                )
                                Spacer(modifier=Modifier.padding(2.dp))
                                Text(theInfoIwant.model)
                            }

                            Row{
                                Icon(
                                    imageVector = Icons.Default.MailOutline,
                                    contentDescription = "person",
                                    modifier=Modifier.height(16.dp)

                                )
                                Spacer(modifier=Modifier.padding(2.dp))
                                Text(theInfoIwant.passengerCapacity.toString())
                            }
                        }
                        Spacer(modifier=Modifier.padding(8.dp))
                        Column {
                            Row{
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "person",
                                    modifier=Modifier.height(16.dp)
                                )
                                Spacer(modifier=Modifier.padding(2.dp))
                                Text(theInfoIwant.luggageCapacity)
                            }

                            Row{
                                Icon(
                                    imageVector = Icons.Default.Build,
                                    contentDescription = "person",
                                    modifier=Modifier.height(16.dp)
                                )
                                Spacer(modifier=Modifier.padding(2.dp))
                                Text(theInfoIwant.category)
                            }
                        }
                        Spacer(modifier=Modifier.padding(8.dp))
                        Column(){
                            Text("vehicle Type", style = TextStyle(fontFamily = Vold))
                            Text(theInfoIwant.type)
                        }
                        Spacer(modifier=Modifier.padding(8.dp))
                        Column(){
                            Text("Fuel Type", style = TextStyle(fontFamily = Vold))
                            Text(theInfoIwant.fuelType)
                        }
                    }
                }
            }

            Column(modifier=Modifier.padding(16.dp)){
                Text("Price per day", style = TextStyle(fontFamily = Vold))
                Text("1500 ETB",style = TextStyle(fontFamily = Inter))
            }

            Card(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors=CardDefaults.cardColors(
                    containerColor = Color(0xFFEC7320),
                    contentColor = Color.White
                ),
                shape= RoundedCornerShape(8.dp)
            ){
                Column(modifier=Modifier
                    .padding(8.dp)
                    .fillMaxWidth()){
                    Row(modifier=Modifier
                        .padding(8.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Column {
                            Text("Price per day", style = TextStyle(fontFamily = Vold))
                            Text("1500 ETB",style = TextStyle(fontFamily = Inter))
                        }
                        Column {
                            Text("Price per day", style = TextStyle(fontFamily = Vold))
                            Text("1500 ETB",style = TextStyle(fontFamily = Inter))
                        }
                    }
                    Row(modifier=Modifier
                        .padding(8.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Column {
                            Text("Price per day", style = TextStyle(fontFamily = Vold))
                            Text("1500 ETB",style = TextStyle(fontFamily = Inter))
                        }
                        Column {
                            Text("Price per day", style = TextStyle(fontFamily = Vold))
                            Text("1500 ETB",style = TextStyle(fontFamily = Inter))
                        }
                    }
                }
                Column(modifier=Modifier
                    .padding(8.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Text("Price per day", style = TextStyle(fontFamily = Vold))
                    Text("1500 ETB",style = TextStyle(fontFamily = Inter))
                }
            }
            Button(onClick = {
                    viewModel.checkLegitimacy()
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

        IconButton(onClick = {}, modifier=Modifier.padding(4.dp)) {
            Icon(
                imageVector= Icons.Default.KeyboardArrowLeft,
                contentDescription ="back icon",
                modifier=Modifier
                    .padding(4.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    ),
            )
        }
    }
}


