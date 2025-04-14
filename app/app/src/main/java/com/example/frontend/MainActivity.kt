package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontend.network.authApi
import com.example.frontend.repositories.authRepository
import com.example.frontend.screens.LoginScreen
import com.example.frontend.ui.theme.FrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val api=authApi.create()
        val repository=authRepository(api)

        setContent {
            val navController=rememberNavController()
            NavHost(navController=navController, startDestination="login"){
                composable(route="login"){
                    LoginScreen(modifier=Modifier.fillMaxSize(),repository,navController)
                }
                composable(route="sign_up"){

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