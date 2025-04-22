package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.car_booking_and_inventory_management.screens.Signup1
import com.example.car_booking_and_inventory_management.screens.Signup2
import com.example.car_booking_and_inventory_management.viewModels.SignupViewModel
import com.example.frontend.DataStore.TokenManager
import com.example.frontend.network.authApi
import com.example.frontend.repositories.authRepository
import com.example.frontend.screens.LoginScreen
import com.example.frontend.ui.theme.FrontendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController=rememberNavController()
            NavHost(navController=navController, startDestination="login"){
                composable(route="login"){
                    LoginScreen(modifier=Modifier.fillMaxSize(),navController)
                }
                navigation(
                    startDestination="sign_up1",
                    route="sign_up_flow"
                ){
                    composable(route="sign_up1"){ backStackEntry->
                        val viewModel:SignupViewModel=viewModel(backStackEntry)
                        Signup1(modifier = Modifier,navController,viewModel)
                    }
                    composable(route="sign_up2") {backStackEntry->
                        val viewModel:SignupViewModel=viewModel(backStackEntry)
                        Signup2(modifier=Modifier,navController,viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FrontendTheme {
        Greeting("Android")
    }
}