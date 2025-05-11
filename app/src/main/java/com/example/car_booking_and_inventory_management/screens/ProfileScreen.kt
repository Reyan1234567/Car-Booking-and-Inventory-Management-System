package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.car_booking_and_inventory_management.AppModule.BASE_URL
import com.example.car_booking_and_inventory_management.data.UploadResponse
import com.example.car_booking_and_inventory_management.data.accountEdit
import com.example.car_booking_and_inventory_management.ui.theme.Inter
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: AuthViewModel) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState=remember{ SnackbarHostState() }
    val saveResult=viewModel.saveResult.collectAsState()

    val Profilelauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateState("profileUri", uri)
    }

    val Licencselauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateState("licenseUri", uri)
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(saveResult){
        val result=saveResult.value
        result?.onSuccess {
            snackbarHostState.showSnackbar("changes saved successfully")
        }?.onFailure {
            snackbarHostState.showSnackbar(it.message.toString())
        }
    }

    BackHandler(enabled = state.isntBackable, onBack = {
        Toast.makeText(context, "Save changes before exiting", Toast.LENGTH_SHORT).show()
        viewModel.updateBackable(false)
    })


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (state.isntBackable) {
                                navController.popBackStack()
                            } else {
                                runBlocking { snackbarHostState.showSnackbar("Save your changes before exiting") }
                            }
                        }
                )
                Text(
                    text = "Profile",
                    textAlign = TextAlign.Center,
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Vold,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            Text("   ")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Profile Image Section
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.LightGray, shape = CircleShape),
                contentAlignment = Alignment.BottomEnd
            ) {
                when {
                    state.profileUri != null -> {
                        Image(
                            painter = rememberImagePainter(state.profileUri),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    state.profileImageUrl!=null -> {
                        Image(
                            painter = rememberImagePainter(state.profileImageUrl),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize(0.6f)
                                .align(Alignment.Center)
                        )
                    }
                }

                Icon(
                    imageVector = if (state.profileUri != null || state.profileImageUrl!=null) Icons.Default.Edit else Icons.Default.Add,
                    contentDescription = "Profile Picture Action",
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White, shape = CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                        .padding(2.dp)
                        .clickable {
                            Profilelauncher.launch("image/*")
                        }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Details Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = {},
                    readOnly=true,
                    label = { Text("Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontFamily = Inter)
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = {viewModel.updateField("email",it) },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontFamily = Inter)
                )

                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = { viewModel.updateField("phoneNumber",it) },
                    label = { Text("Phone Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontFamily = Inter)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // License Photo Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.licenseImageUrl==null && state.licenseUri == null) {
                        Text(
                            text = "License Photo",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { Licencselauncher.launch("image/*") },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE36911))
                        ) {
                            Text("Upload", color = Color.Black)
                        }

                    } else {
                        Image(
                            painter = rememberImagePainter(state.licenseUri ?: state.licenseImageUrl),
                            contentDescription = "License Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Button(
                            onClick = { Licencselauncher.launch("image/*") },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE36911))
                        ) {
                            Text("Change upload", color = Color.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            Button(
                onClick = {
                    runBlocking{
                        var profileId=UploadResponse()
                        var licenseId=UploadResponse()
                        if(state.profileUri!=null){
                            profileId=viewModel.uploadProfile(context)
                            Log.v(TAG, profileId.toString()+" "+profileId.id)
                        }
                        if(state.licenseUri!=null){
                            licenseId=viewModel.uploadLicense(context)
                            Log.v(TAG, licenseId.toString()+" "+licenseId.id)
                        }
                        viewModel.saveProfile(profileId.id, licenseId.id)
                    }

                },
                enabled = state.isntBackable,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE36911))
            ) {
                Text(text = "Save Changes", fontSize = 16.sp)
            }
        }
    }
}




