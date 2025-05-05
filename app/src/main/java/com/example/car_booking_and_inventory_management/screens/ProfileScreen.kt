package com.example.car_booking_and_inventory_management.screens

import android.annotation.SuppressLint
import android.net.Uri
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
import com.example.car_booking_and_inventory_management.data.UploadResponse
import com.example.car_booking_and_inventory_management.data.accountEdit
import com.example.car_booking_and_inventory_management.ui.theme.Inter
import com.example.car_booking_and_inventory_management.ui.theme.Vold
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf(runBlocking { viewModel.getUsername().toString() }) }
    val UN = runBlocking { viewModel.getUsername().toString() }
    var email by remember { mutableStateOf(runBlocking { viewModel.getEmail().toString() }) }
    val EM = runBlocking { viewModel.getEmail().toString() }
    var contactNumber by remember { mutableStateOf(runBlocking { viewModel.getPhoneNumber().toString() }) }
    val CN = runBlocking { viewModel.getPhoneNumber().toString() }
    val context = LocalContext.current
    val profileImage = runBlocking { viewModel.getProfilePhoto().toString() }
    val licenseImage = runBlocking { viewModel.getLicensePhoto().toString() }
    var ProfileUri by remember { mutableStateOf<Uri?>(null) }
    var LicenseUri by remember { mutableStateOf<Uri?>(null) }
    val snackbarHostState=remember{ SnackbarHostState() }


    val Profilelauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        ProfileUri = uri
    }

    val Licencselauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        LicenseUri = uri
    }

    val licenseResult = viewModel.profileUploadResult.collectAsState()
    val profileResult = viewModel.licenseUploadResult.collectAsState()
    val isLoading = viewModel.isLoading3.collectAsState()

    var backEnabler by remember { mutableStateOf(false)}

    var url by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    LaunchedEffect(username,email,contactNumber,ProfileUri,LicenseUri) {
        if (username != UN || email != EM || contactNumber != CN || ProfileUri != null || LicenseUri != null) {
            backEnabler = true
        } else {
            backEnabler = false
        }
    }

    BackHandler(enabled = backEnabler, onBack = {
        runBlocking{ snackbarHostState.showSnackbar("Save your changes before exiting") }
        backEnabler = false
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
                            if (backEnabler) {
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
                    ProfileUri != null -> {
                        Image(
                            painter = rememberImagePainter(ProfileUri),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    profileImage.isNotEmpty() -> {
                        Image(
                            painter = rememberImagePainter(profileImage),
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
                    imageVector = if (ProfileUri != null || profileImage.isNotEmpty()) Icons.Default.Edit else Icons.Default.Add,
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
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontFamily = Inter)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(fontFamily = Inter)
                )

                OutlinedTextField(
                    value = contactNumber,
                    onValueChange = { contactNumber = it },
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
                    if (licenseImage=="null" && LicenseUri == null) {
                        Text(
                            text = "License Photo",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                    } else {
                        Image(
                            painter = rememberImagePainter(LicenseUri ?: licenseImage),
                            contentDescription = "License Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Box{
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
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            Button(
                onClick = {
                    if(LicenseUri != null) {
                        viewModel.uploadLicense(uri = LicenseUri!!, context = context)
                        licenseResult.value?.onSuccess {
                            url=it.url.toString()
                            id=it._id.toString()
                            runBlocking{ snackbarHostState.showSnackbar(it.message.toString()) }
                        }?.onFailure {
                            runBlocking { snackbarHostState.showSnackbar(it.message.toString()) }
                        }
                    }
                    if (ProfileUri != null) {
                        viewModel.uploadProfile(uri = ProfileUri!!, context = context)
                        profileResult.value?.onSuccess {
                            url=it.url.toString()
                            id=it._id.toString()
                            runBlocking{ snackbarHostState.showSnackbar(it.message.toString()) }
                        }?.onFailure {
                            runBlocking { snackbarHostState.showSnackbar(it.message.toString()) }
                        }                    }

                    val userId = runBlocking { viewModel.getUserId().toString() }
                    val body = accountEdit(
                        username = username,
                        email = email,
                        profileImage = url,
                        licenceImage = id,
                        phoneNumber = contactNumber
                    )
                    runBlocking { viewModel.editAccount(userId, body) }
                },
                enabled = backEnabler,
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




/*
@Composable
fun ProfileField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 4.dp)
    ) {
        Text(text = label, fontSize = 14.sp, style = TextStyle(fontFamily = Vold))
        TextField(
            value = value,
            onValueChange = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.LightGray
            )

        )
    }
}}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 4.dp)
    ) {
        Text(text = label, fontSize = 14.sp, style = TextStyle(fontFamily = Vold))
        TextField(
            value = value,
            onValueChange = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.LightGray
            )

        )
    }
}*/

//@Preview(showBackground = true)
//@Composable
//private fun screen() {
//    ProfileScreen()
//}