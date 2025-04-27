package com.example.car_booking_and_inventory_management.screens

import android.content.ContentValues.TAG
import android.provider.CallLog
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.Location
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen1(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel:CarFilterViewModel
) {
    var searchResultsLocations=viewModel.carsGetResponse1.collectAsState()


    var search by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf(emptyList<Location>()) }

    var notFound by remember { mutableStateOf("") }

    LaunchedEffect(searchResultsLocations.value) {
        searchResultsLocations.value?.onSuccess{filteredCars->
            searchResult=filteredCars
            if(filteredCars.isEmpty()){
                notFound="NOT FOUND"
            }
            else{
                notFound=""
            }
        }?.onFailure{

        }
    }

    Column(
        modifier=Modifier.padding(16.dp)
    ){
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 0.dp),
            horizontalArrangement = Arrangement.Start
        ){
            IconButton(
                onClick = {navController.popBackStack()}
            ) {
                Icon(
                    imageVector= Icons.Default.ArrowBack,
                    contentDescription="back button"
                )
            }
            Text(
                text="Cars",
                style = TextStyle(fontSize=24.sp, fontFamily = Vold),
                modifier=Modifier.padding(top=10.dp))
        }
        TextField(
            value=search,
            onValueChange = {
                search=it
                viewModel.getLocations1(search)
            },
            placeholder={
                Text("Enter pickup location")
            }
            ,modifier=Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            , colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            )
        )
        if(searchResult.isNotEmpty()){
            LazyColumn(modifier=Modifier.padding(8.dp)){
                items(searchResult) { location ->
                    LocationCard1(modifier = Modifier, location = location, {
                        viewModel.updateDropOff("${location.name}, ${location.city}")
                        Log.v(TAG,viewModel.pickUp)
                        navController.popBackStack()
                    })
                }
            }
        }
        else{
            Text(text=notFound, modifier=Modifier.padding(8.dp), style = TextStyle(fontSize = 16.sp , fontFamily = Vold))
        }
    }
}

@Composable
fun LocationCard1(modifier: Modifier = Modifier, location:Location, onClick:()->Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)){
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription="icon",
            modifier=Modifier
                .width(40.dp)
                .padding(top = 12.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(
                text = location.name, style = TextStyle(fontSize = 16.sp, fontFamily = Vold),
                modifier = Modifier
                    .clickable(
                        onClick = onClick
                    )
            )

            Text(
                text = location.city,
                style = TextStyle(fontSize = 12.sp, fontFamily = Vold, color = Color.Gray),
                modifier = Modifier
                    .clickable(
                        onClick = onClick
                    )
            )

        }
    }
}
