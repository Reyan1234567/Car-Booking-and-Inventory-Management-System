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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.car_booking_and_inventory_management.screens.SignUpScreen
import com.example.car_booking_and_inventory_management.screens.Signup1
import com.example.car_booking_and_inventory_management.screens.Signup2
import com.example.frontend.DataStore.TokenManager
import com.example.frontend.network.authApi
import com.example.frontend.repositories.authRepository
import com.example.frontend.screens.LoginScreen
import com.example.frontend.ui.theme.FrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tokenManager:TokenManager= TokenManager(context =applicationContext )
        val api=authApi.provideRetrofit(tokenManager).create(authApi::class.java)
        val repository=authRepository(api)

        setContent {
            val navController=rememberNavController()
            NavHost(navController=navController, startDestination="login"){
                composable(route="login"){
                    LoginScreen(modifier=Modifier.fillMaxSize(),repository,navController,tokenManager)
                }
                composable(route="sign_up"){
                    SignUpScreen(modifier = Modifier,repository=repository,navController)
                }
                composable(route="sign_up1"){
                    Signup1(modifier = Modifier,navController)
                }
                composable(route="sign_up2") {
                    Signup2(modifier=Modifier)
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