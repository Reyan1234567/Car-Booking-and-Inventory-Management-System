package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarResponse
import com.example.car_booking_and_inventory_management.data.FilterItem
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: CarFilterViewModel) {
    var search by remember { mutableStateOf("") }

    val filters = remember {mutableStateListOf(
        FilterItem(name = "Toyota Corolla"),
        FilterItem(name = "Honda Civic"),
        FilterItem(name = "Ford Mustang"),
        FilterItem(name = "Chevrolet Camaro"),
        FilterItem(name = "BMW 3 Series"),
        FilterItem(name = "Mercedes-Benz C-Class"),
        FilterItem(name = "Audi A4"),
        FilterItem(name = "Tesla Model 3"),
        FilterItem(name = "Volkswagen Golf"),
        FilterItem(name = "Subaru Impreza"),
        FilterItem(name = "Nissan Altima"),
        FilterItem(name = "Mazda3"),
        FilterItem(name = "Hyundai Elantra"),
        FilterItem(name = "Kia Forte"),
        FilterItem(name = "Dodge Charger")
    )}



    var CarSearchs=viewModel.carsFilterResponse.collectAsState()
    var carsearchs=CarSearchs.value

    var searchResultArray by remember { mutableStateOf(emptyList<CarResponse>()) }

    var filterFlag by remember { mutableStateOf("") }

        val finalResults = searchResultArray.filter { car ->
            val matchesSearch = search.isBlank() ||
                    car.name?.contains(search, ignoreCase = true) ?: false ||
                    car.make?.contains(search, ignoreCase = true) ?: false ||
                    car.model?.contains(search, ignoreCase = true) ?: false ||
                    car.category?.contains(search, ignoreCase = true) ?: false ||
                    car.type?.contains(search, ignoreCase = true) ?: false ||
                    car.transmissionType?.contains(search, ignoreCase = true) ?: false

            val matchesFilter = filterFlag.isBlank() ||
                    car.name?.contains(filterFlag, ignoreCase = true) ?: false ||
                    car.make?.contains(filterFlag, ignoreCase = true) ?: false ||
                    car.model?.contains(filterFlag, ignoreCase = true) ?: false ||
                    car.category?.contains(filterFlag, ignoreCase = true) ?: false ||
                    car.type?.contains(filterFlag, ignoreCase = true) ?: false ||
                    car.transmissionType?.contains(filterFlag, ignoreCase = true) ?: false

            matchesSearch && matchesFilter
        }


    Column(modifier=Modifier.padding(end=14.dp, top=24.dp, start=12.dp)){
        Row(
            modifier=Modifier.fillMaxWidth()
        ){
            Icon(
                imageVector= Icons.Default.KeyboardArrowLeft,
                contentDescription="back",
                modifier=Modifier
                    .padding(8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable{
                        navController.popBackStack()
                    }
                    .height(45.dp)
                    .padding(8.dp)


            )
            TextField(
                value = search,
                onValueChange={
                    search=it },
                placeholder = {Text("Search", style = TextStyle(fontFamily = Vold), color = Color.Gray)},
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.LightGray
                ),
                modifier=Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )

        }

        LazyRow(
            modifier=Modifier.padding(8.dp)
        ){
            items(filters){filter->
                val isSelected = filter.value.value
                if(!isSelected){
                    FilterItems(modifier =Modifier,{
                        filters.forEach { it ->
                            it.value.value = false
                        }
                        filter.value.value=true
                        filterFlag=filter.name
                        Log.v(TAG,"${filter.value.value}")
                    },filter.name,0xFFC5C5C5,0xFF000000 )
                }
                else{
                    FilterItems(modifier=Modifier,{
                        filter.value.value=false
                        filterFlag=""
                        Log.v(TAG,"${filter.value.value}")
                    },filter.name,0xFF2F2F2F , 0xFFFFFFFF)
                }
            }
        }
        Spacer(modifier=Modifier.padding(4.dp))

        carsearchs?.onSuccess {cars->
            LazyColumn(modifier=Modifier.padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally){
                searchResultArray=cars
                if(finalResults!= emptyList<CarResponse>()){
                    items(finalResults){car->
                        CarDisplay(modifier =Modifier,car) {
                            car.plate?.let { Log.v(TAG, it) }
                            navController.navigate("singleCar/${car._id}")
                        }
                    }
                }
                else{
                    item{ Text(text = "Nothing found", style = TextStyle(fontSize = 20.sp, fontFamily = Vold), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(16.dp)) }
                }
            }
        }?.onFailure {
           Text("No Cars Found")
        }
    }
}


@Composable
fun FilterItems(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Long,
    contentColor: Long
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        color = Color(color),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(fontFamily = Vold, fontSize = 12.sp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            color=Color(contentColor)
        )
    }
}


@Composable
fun CarDisplay(modifier: Modifier = Modifier, car: CarResponse, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp).clickable{
            onClick()
        },
        colors= CardDefaults.cardColors(containerColor = Color(0xFFEC7320)),
        shape= RoundedCornerShape(12.dp)
    ){
       Row(modifier = Modifier
           .fillMaxWidth()
           .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
       ){
            Column() {
                car.name?.let { Text(text = it, style = TextStyle(fontSize = 20.sp, fontFamily = Vold)) }
                Spacer(modifier = Modifier.padding(6.dp))
                Row(horizontalArrangement = Arrangement.Start) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "bag",
                        modifier=Modifier.width(15.dp)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    car.luggageCapacity?.let { Text(text = it) }

                    Spacer(modifier = Modifier.padding(12.dp))

                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "person",
                        modifier=Modifier.width(15.dp)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(text = car.passengerCapacity.toString())
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Text("Type", style = TextStyle(fontSize = 16.sp, fontFamily = Vold))
                Spacer(modifier = Modifier.padding(2.dp))
                car.type?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontSize = 16.sp, fontFamily = Vold, color = Color(0xFF554234))
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "${car.price}",
                    style = TextStyle(fontSize = 20.sp, fontFamily = Vold)
                )

            }
           AsyncImage(
               model = car.imageUrl,
               contentDescription = "Example Image",
               modifier = Modifier
                   .height(200.dp)
                   .width(150.dp)
                   .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
           )
        }
    }
}

