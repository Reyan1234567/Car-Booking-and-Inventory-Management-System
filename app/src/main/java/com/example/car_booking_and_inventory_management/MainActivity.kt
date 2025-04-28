package com.example.car_booking_and_inventory_management

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.car_booking_and_inventory_management.screens.CarSearchFilter
import com.example.car_booking_and_inventory_management.screens.Signup1
import com.example.car_booking_and_inventory_management.screens.Signup2
import com.example.car_booking_and_inventory_management.viewModels.SignupViewModel
import com.example.car_booking_and_inventory_management.screens.LoginScreen
import com.example.car_booking_and_inventory_management.screens.SearchScreen
import com.example.car_booking_and_inventory_management.screens.SearchScreen1
import com.example.car_booking_and_inventory_management.screens.SearchViewScreen
import com.example.car_booking_and_inventory_management.screens.SingleCarScreen
import com.example.car_booking_and_inventory_management.ui.theme.FrontendTheme
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController=rememberNavController()
            NavHost(navController=navController, startDestination="home"){
                composable(route="login"){
                    LoginScreen(modifier=Modifier.fillMaxSize(),navController)
                }
                navigation(
                    startDestination = "carSearchFilter",
                    route="home"
                ){
                    composable(route="carSearchFilter") {backStackEntry->
                        val parentEntry=remember(backStackEntry){
                            navController.getBackStackEntry("home")
                        }
                        val viewModel:CarFilterViewModel= hiltViewModel(parentEntry)
                        CarSearchFilter(modifier = Modifier,navController, viewModel)
                    }
                    composable(route="searchPickups") {backStackEntry->
                        val parentEntry=remember(backStackEntry){
                            navController.getBackStackEntry("home")
                        }
                        val viewModel:CarFilterViewModel= hiltViewModel(parentEntry)
                        SearchScreen(modifier = Modifier,navController, viewModel)
                    }
                    composable(route="searchDropOffs") {backStackEntry->
                        val parentEntry=remember(backStackEntry){
                            navController.getBackStackEntry("home")
                        }
                        val viewModel:CarFilterViewModel= hiltViewModel(parentEntry)
                        SearchScreen1(modifier = Modifier,navController, viewModel)
                    }
                    composable(route="searchResults") {backStackEntry->
                        val parentEntry=remember(backStackEntry){
                            navController.getBackStackEntry("home")
                        }
                        val viewModel:CarFilterViewModel= hiltViewModel(parentEntry)
                        SearchViewScreen(modifier = Modifier,navController, viewModel)
                    }
                    composable(route="singleCar/{name}") {backStackEntry->
                        val name=backStackEntry.arguments?.getString("name")
                        val parentEntry=remember(backStackEntry){
                            navController.getBackStackEntry("home")
                        }
                        val viewModel:CarFilterViewModel= hiltViewModel(parentEntry)
                        SingleCarScreen(modifier = Modifier,navController, viewModel,name!!)
                    }
                }
                composable(route="history"){

                }
                composable(route="profile"){

                }
                navigation(
                    startDestination="sign_up1",
                    route="sign_up_flow"
                ){
                    composable(route="sign_up1"){ backStackEntry->
                        val parentEntry=remember(backStackEntry){
                            navController.getBackStackEntry("sign_up_flow")
                        }
                        val viewModel:SignupViewModel= hiltViewModel(parentEntry)
                        Signup1(modifier = Modifier,navController,viewModel)
                    }
                    composable(route="sign_up2") {backStackEntry->
                        val parentEntry=remember(backStackEntry){
                            navController.getBackStackEntry("sign_up_flow")
                        }
                        val viewModel:SignupViewModel= hiltViewModel(parentEntry)
                        Signup2(modifier=Modifier,navController,viewModel)
                    }
                }
            }
        }
    }
}
