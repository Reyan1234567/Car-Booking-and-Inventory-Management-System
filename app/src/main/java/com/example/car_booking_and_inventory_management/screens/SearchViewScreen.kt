package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.car_booking_and_inventory_management.data.Car
import com.example.car_booking_and_inventory_management.data.CarSearchResults
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: CarFilterViewModel) {
    var search by remember { mutableStateOf("") }
    var filters= listOf("cars","racks","jimmy butler","rosa parks","obama")

//    var searchResults by remember { mutableStateOf(emptyList<CarSearchResults>()) }

    var CarSearchs=viewModel.carsFilterResponse.collectAsState()
    var carsearchs=CarSearchs.value

    var searchResultArray by remember { mutableStateOf(emptyList<Car>()) }
    var filteredResultArray by remember { mutableStateOf(emptyList<Car>()) }

    var filterFlag by remember { mutableStateOf("") }
    var filteredFlagArray by remember { mutableStateOf(emptyList<Car>()) }


//    LaunchedEffect(carsearchs) {
//        carsearchs?.onSuccess {
//
//        }?.onFailure {
//
//        }
//    }
    var filterAndSearchResult= remember(filteredResultArray, filteredFlagArray){
        searchResultArray.filter { filteredResultArray.contains(it) && filteredFlagArray.contains(it) }
    }

    Column(modifier=Modifier.padding(16.dp, top=20.dp)){
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
                    .padding(14.dp)
            )
            Spacer(modifier = Modifier.padding(2.dp))
            TextField(
                value = search,
                onValueChange={
                    search=it
                    filteredResultArray=searchResultArray.filter{
                        it.name.contains(search)||it.make.contains(search)||it.model.contains(search)||it.category.contains(search)||it.type.contains(search)||it.transmissionType.contains(search)
                    }
                    },
                placeholder = {Text("Search", style = TextStyle(fontFamily = Vold), color = Color.Gray)},
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.LightGray
                ),
                modifier=Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp)),
                trailingIcon = {
                    IconButton(onClick = {}){
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )

        }

        LazyRow(
            modifier=Modifier.padding(8.dp)
        ){
            items(filters){filter->
                FilterItems(modifier=Modifier,{
                    filterFlag=filter
                    filteredFlagArray=searchResultArray.filter{on->
                        on.name.contains(filterFlag)||on.make.contains(filterFlag)||on.model.contains(filterFlag)||on.category.contains(filterFlag)||on.type.contains(filterFlag)||on.transmissionType.contains(filterFlag)
                    }
                },filter)

            }
        }
        Spacer(modifier=Modifier.padding(4.dp))

        carsearchs?.onSuccess {cars->
            LazyColumn(modifier=Modifier.padding(2.dp)){
                searchResultArray=cars
                filteredFlagArray=searchResultArray
                filteredResultArray=searchResultArray
                items(filterAndSearchResult){car->
                    CarDisplay(modifier=Modifier,car,{
                    })
                }
            }
        }?.onFailure {
           Text("No Cars Found ")
        }
    }
}


@Composable
fun FilterItems(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        color = Color.LightGray,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(fontFamily = Vold, fontSize = 12.sp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}


@Composable
fun CarDisplay(modifier: Modifier = Modifier, car:Car, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors= CardDefaults.cardColors(containerColor = Color(0xFFEC7320)),
        shape= RoundedCornerShape(12.dp)
    ){
       Row(modifier = Modifier
           .fillMaxWidth()
           .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
       ){
            Column() {
                Text(text = car.name, style = TextStyle(fontSize = 20.sp, fontFamily = Vold))
                Spacer(modifier = Modifier.padding(6.dp))
                Row(horizontalArrangement = Arrangement.Start) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "bag",
                        modifier=Modifier.width(15.dp)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(text = car.luggageCapacity)

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
                Text(
                    text = car.type,
                    style = TextStyle(fontSize = 16.sp, fontFamily = Vold, color = Color(0xFF554234))
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "${car.price}",
                    style = TextStyle(fontSize = 20.sp, fontFamily = Vold)
                )

            }
           Image(
               painter = rememberAsyncImagePainter(car.imageUrl),
               contentDescription = "",
               modifier=Modifier.width(120.dp),
               contentScale = ContentScale.Crop
           )
        }
    }
}