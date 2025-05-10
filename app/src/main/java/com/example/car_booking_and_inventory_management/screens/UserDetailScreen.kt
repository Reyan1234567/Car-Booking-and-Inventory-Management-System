package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.car_booking_and_inventory_management.data.UserPPLP
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AdminViewModel,
    id: String
) {
    val context = LocalContext.current
    val userResult = viewModel.UserResponse.collectAsState().value
    var users by remember { mutableStateOf(emptyList<UserPPLP>()) }
    var user by remember { mutableStateOf<UserPPLP?>(null) }

    LaunchedEffect(Unit) {
        userResult?.onSuccess {
            users = it
            user = users.firstOrNull { usr -> usr._id == id }
            Log.v(TAG,user.toString())
        }?.onFailure {
            Log.v(TAG,user.toString())
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("") },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
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
                    titleContentColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            user?.let { user ->
                // Profile Image
                if(user.PP?.url!=null){
                    Image(
                    painter = rememberAsyncImagePainter(user.PP.url),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(140.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(Color.White, shape = RoundedCornerShape(7.dp))
                )
                }else{
                    Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription ="",
                    modifier=Modifier.size(140.dp).align(Alignment.CenterHorizontally) )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // User ID
                Text(
                    text = "User ID: ${user._id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )

                // User Details
                DetailRow("Username", "Email", user.username, user.email)
                DetailRow("First Name", "Last Name", user.firstName, user.lastName)
                DetailRow("Phone Number", "Role", user.phoneNumber, user.birthDate)
                
                // License Image
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "License Photo",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                if(user.LP?.url!=null){
                    Image(
                        painter = rememberAsyncImagePainter(user.LP.url),
                        contentDescription = "License",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
                            .background(Color.White, shape = RoundedCornerShape(7.dp))
                    )
                }
                else{
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription ="",
                        modifier=Modifier.size(140.dp).align(Alignment.CenterHorizontally) )
                }


                Spacer(modifier = Modifier.height(32.dp))
            } ?: run {
                // Show a loading or error message if user is null
                Text(
                    text = "${userResult}",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

//@Composable
//fun DetailUserRow(
//    label1: String,
//    label2: String,
//    value1: String,
//    value2: String
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column(modifier = Modifier.weight(1f)) {
//            Text(text = label1, fontSize = 14.sp, color = Color.Gray)
//            Text(text = value1, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//        }
//        Spacer(modifier = Modifier.width(40.dp))
//        Column(
//            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.End
//        ) {
//            Text(text = label2, fontSize = 14.sp, color = Color.Gray)
//            Text(text = value2, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}