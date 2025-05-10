//package com.example.car_booking_and_inventory_management.screens
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.style.LineHeightStyle
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.runBlocking
//import java.time.format.TextStyle
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AdminProfile(modifier: Modifier = Modifier,
//        userName: String = "John Doe",
//        email: String = "john.doe@example.com",
//        phone: String = "+1 234 567 890",
//        onLogout: () -> Unit
//    ) {
//var logoutScreen by remember {mutableStateOf(false)}
//
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Profile") },
//                    actions = {
//                        TextButton(onClick = onLogout) {
//                            Text("Logout", color = Color.White)
//                        }
//                    }
//                )
//            }
//        ) { innerPadding ->
//            Box(
//
//            ){
//                Column(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    imageVector = Icons.Default.AccountCircle,
//                    contentDescription = "Profile Icon",
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(top = 32.dp),
//                    tint = Color.Gray
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                ProfileItem(label = "Name", value = userName)
//                ProfileItem(label = "Email", value = email)
//                ProfileItem(label = "Phone", value = phone)
//            }
//
//                if(logoutScreen){
//                    LogoutConfirmationScreen(
//                        onConfirmLogout = { runBlocking { viewModel.logout()}
//                            navController.navigate("sign_up_flow") },
//                        onCancel = { logoutScreen=false }
//                    )
//                }
//        }
//    }
//}
//
//    @Composable
//    fun ProfileItem(label: String, value: String) {
//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)) {
//            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
//            Text(text = value, style = MaterialTheme.typography.bodyLarge)
//        }
//    }
//
