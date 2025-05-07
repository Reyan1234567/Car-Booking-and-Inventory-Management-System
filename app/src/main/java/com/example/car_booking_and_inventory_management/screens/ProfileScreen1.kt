package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.vo.Warning
import coil.compose.rememberImagePainter
import com.example.car_booking_and_inventory_management.AppModule.BASE_URL
import com.example.car_booking_and_inventory_management.R
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import kotlinx.coroutines.runBlocking

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen1(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel:AuthViewModel
) {

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var logoutScreen by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController=navController) }
    ){
        Box(){
            if(logoutScreen){
                LogoutConfirmationScreen(
                    onConfirmLogout = { runBlocking { viewModel.logout()}
                        navController.navigate("sign_up_flow") },
                    onCancel = { navController.popBackStack() }
                )
            }
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Header Row (Logo + Title + Exit Icon)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "   ", color = Color.Transparent) // Spacer
                    Text(
                        text = "Profile",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = Vold,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Exit",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                logoutScreen = true
                            }
                            ,tint=Color(0xFFC90000)
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))

                // Profile Image (Placeholder)
                if (state.profileImageUrl != "") {
                    Log.v("TAG", "profile image url: ${state.profileImageUrl}")
                    Image(
                        painter = rememberImagePainter(state.profileImageUrl), // Replace with your drawable
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentDescription = ""

                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // User Details (Username, Email, Phone)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    state.username?.let { it1 ->
                        Text(
                            text = it1, // Replace with actual data
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    state.email?.let { it1 ->
                        Text(
                            text = it1, // Replace with actual data
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    state.phoneNumber?.let { it1 ->
                        Text(
                            text = it1, // Replace with actual data
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // License Photo Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE59F6A),
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    if (state.licenseImageUrl != "") {
                        Log.v("TAG", "license image url: ${state.licenseImageUrl}")
                        Image(
                            painter = rememberImagePainter( state.licenseImageUrl), // Replace with your drawable
                            modifier = Modifier.size(350.dp),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No license Photo uploaded",
                                style = TextStyle(
                                    fontFamily = Vold,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        navController.navigate("profile1") // Replace with your route
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE36911),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Edit Profile", fontSize = 16.sp)
                }
            }

            if(logoutScreen){
                LogoutConfirmationScreen(
                    onConfirmLogout = { runBlocking { viewModel.logout()}
                        navController.navigate("sign_up_flow") },
                    onCancel = { logoutScreen=false }
                )
            }
        }
    }
}

@Composable
fun LogoutConfirmationScreen(
    modifier: Modifier = Modifier,
    onConfirmLogout: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Warning icon
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning",
            modifier = Modifier.size(64.dp),
            tint = Color(0xFFFFA000) // Amber color
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "Logout?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Message
        Text(
            text = "Are you sure you want to logout from your account?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cancel button
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Text("Cancel", color = Color.Black)
            }

            // Logout button
            Button(
                onClick = onConfirmLogout,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE36911), // Orange color
                    contentColor = Color.White
                )
            ) {
                Text("Logout")
            }
        }
    }
}

