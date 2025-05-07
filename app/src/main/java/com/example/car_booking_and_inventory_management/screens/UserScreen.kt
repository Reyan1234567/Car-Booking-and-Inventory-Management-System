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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.data.BookingTable
import com.example.car_booking_and_inventory_management.data.UsersTable
import com.example.car_booking_and_inventory_management.ui.theme.Vold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(" ") },
                modifier = modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Image(
                            painter= painterResource(R.drawable.polo),
                            contentDescription =""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription =""
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
    ){ innerPadding->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()){
            Column(modifier=Modifier.padding(20.dp)){
                Text("Users", style= TextStyle(fontSize = 30.sp, fontFamily = Vold))
                Spacer(modifier=Modifier.padding(5.dp))
                LazyRow(){
                    item{ UsersTableHeader() }

//                    UserList.mapIndexed{index, user->
//                        TableRow(user)
//                    }
                }
            }
        }
    }
}



@Composable
fun UsersTableHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Text("Username", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
        Text("Email", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.3f))
        Text("First Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
        Text("Last Name", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
        Text("Phone", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
        Spacer(modifier = Modifier.weight(0.1f)) // for edit button
    }
}

@Composable
fun UsersTableRow(user: UsersTable, modifier: Modifier = Modifier, onEditClick: () -> Unit = {}) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(user.username, modifier = Modifier.weight(0.2f))
        Text(user.email, modifier = Modifier.weight(0.3f))
        Text(user.firstname, modifier = Modifier.weight(0.2f))
        Text(user.lastname, modifier = Modifier.weight(0.2f))
        Text(user.phoneNumber, modifier = Modifier.weight(0.2f))
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






@Preview(showBackground = true)
@Composable
private fun Ccc() {
    Booking()
}