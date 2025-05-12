package com.example.car_booking_and_inventory_management

import android.annotation.SuppressLint
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
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.car_booking_and_inventory_management.screens.BookingDetailScreen
import com.example.car_booking_and_inventory_management.screens.BookingScreen
import com.example.car_booking_and_inventory_management.screens.CarDetailScreen
import com.example.car_booking_and_inventory_management.screens.CarEditCreate
import com.example.car_booking_and_inventory_management.screens.CarScreen
import com.example.car_booking_and_inventory_management.screens.CarSearchFilter
import com.example.car_booking_and_inventory_management.screens.DashboardPage
import com.example.car_booking_and_inventory_management.screens.FirstPage
import com.example.car_booking_and_inventory_management.screens.Signup1
import com.example.car_booking_and_inventory_management.screens.Signup2
import com.example.car_booking_and_inventory_management.viewModels.SignupViewModel
import com.example.car_booking_and_inventory_management.screens.LoginScreen
import com.example.car_booking_and_inventory_management.screens.ProfileScreen
import com.example.car_booking_and_inventory_management.screens.ProfileScreen1
//import com.example.car_booking_and_inventory_management.screens.ProfileScreen1
import com.example.car_booking_and_inventory_management.screens.SearchScreen
import com.example.car_booking_and_inventory_management.screens.SearchScreen1
import com.example.car_booking_and_inventory_management.screens.SearchViewScreen
import com.example.car_booking_and_inventory_management.screens.SingleCarScreen
import com.example.car_booking_and_inventory_management.screens.UserDetailScreen
import com.example.car_booking_and_inventory_management.screens.UserScreen
import com.example.car_booking_and_inventory_management.ui.theme.FrontendTheme
import com.example.car_booking_and_inventory_management.viewModels.AdminViewModel
import com.example.car_booking_and_inventory_management.viewModels.AuthViewModel
import com.example.car_booking_and_inventory_management.viewModels.CarFilterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "sign_up_flow") {
                navigation(
                    startDestination = "dashboard",
                    route = "admin"
                ) {
                    composable(route = "dashboard") { backStackEntry ->
                        val parentEntry =
                            remember(backStackEntry) { navController.getBackStackEntry("admin") }
                        val viewModel: AdminViewModel = hiltViewModel(parentEntry)
                        DashboardPage(modifier = Modifier, navController, viewModel)
                    }

                    composable(route = "users") { backStackEntry ->
                        val parentEntry =
                            remember(backStackEntry) { navController.getBackStackEntry("admin") }
                        val viewModel: AdminViewModel = hiltViewModel(parentEntry)
                        UserScreen(modifier = Modifier, viewModel, navController)
                    }

                    composable(route = "bookings") { backStackEntry ->
                        val parentEntry =
                            remember(backStackEntry) { navController.getBackStackEntry("admin") }
                        val viewModel: AdminViewModel = hiltViewModel(parentEntry)
                        BookingScreen(modifier = Modifier, viewModel, navController)
                    }

                    composable(route = "cars") { backStackEntry ->
                        val parentEntry =
                            remember(backStackEntry) { navController.getBackStackEntry("admin") }
                        val viewModel: AdminViewModel = hiltViewModel(parentEntry)
                        CarScreen(modifier = Modifier, viewModel, navController)
                    }

                    composable(route="bookingDetail/{id}"){backStackEntry->
                        val id=backStackEntry.arguments?.getString("id")
                        val parentEntry= remember(backStackEntry){
                            navController.getBackStackEntry("admin")
                        }
                        val viewModel:AdminViewModel= hiltViewModel(parentEntry)
                        BookingDetailScreen(Modifier, navController, viewModel, id?:"")
                    }

                    composable(route="userDetail/{id}"){backStackEntry->
                        val id=backStackEntry.arguments?.getString("id")
                        val parentEntry= remember(backStackEntry){
                            navController.getBackStackEntry("admin")
                        }
                        val viewModel:AdminViewModel= hiltViewModel(parentEntry)
                        UserDetailScreen(Modifier, navController, viewModel, id?:"")
                    }

                    composable(route="carDetail/{id}"){backStackEntry->
                        val id=backStackEntry.arguments?.getString("id")
                        val parentEntry= remember(backStackEntry){
                            navController.getBackStackEntry("admin")
                        }
                        val viewModel:AdminViewModel= hiltViewModel(parentEntry)
                        CarDetailScreen(Modifier, navController, viewModel, id?:"")
                    }

                    composable(route="carCreate"){backStackEntry->
                        val parentEntry= remember(backStackEntry){
                            navController.getBackStackEntry("admin")
                        }
                        val viewModel:AdminViewModel= hiltViewModel(parentEntry)
                        CarEditCreate(Modifier, navController, viewModel)
                    }

                    composable(route="carEdit/{id}"){backStackEntry->
                        val id = backStackEntry.arguments?.getString("id")
                        val parentEntry= remember(backStackEntry){
                            navController.getBackStackEntry("admin")
                        }
                        val viewModel:AdminViewModel= hiltViewModel(parentEntry)
                        CarEditCreate(Modifier, navController, viewModel, id ?: "")
                    }
                }
                    navigation(
                        startDestination = "carSearchFilter",
                        route = "home"
                    ) {
                        composable(route = "carSearchFilter") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("home")
                            }
                            val viewModel: CarFilterViewModel = hiltViewModel(parentEntry)
                            CarSearchFilter(modifier = Modifier, navController, viewModel)
                        }
                        composable(route = "searchPickups") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("home")
                            }
                            val viewModel: CarFilterViewModel = hiltViewModel(parentEntry)
                            SearchScreen(modifier = Modifier, navController, viewModel)
                        }
                        composable(route = "searchDropOffs") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("home")
                            }
                            val viewModel: CarFilterViewModel = hiltViewModel(parentEntry)
                            SearchScreen1(modifier = Modifier, navController, viewModel)
                        }
                        composable(route = "searchResults") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("home")
                            }
                            val viewModel: CarFilterViewModel = hiltViewModel(parentEntry)
                            SearchViewScreen(modifier = Modifier, navController, viewModel)
                        }
                        composable(route = "singleCar/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("home")
                            }
                            val viewModel: CarFilterViewModel = hiltViewModel(parentEntry)
                            SingleCarScreen(modifier = Modifier, navController, viewModel, id!!)
                        }
                    }
                    composable(route = "history") {

                    }

                    navigation(
                        startDestination = "firstPage",
                        route = "sign_up_flow"
                    ) {
                        composable(route = "profile1") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("sign_up_flow")
                            }
                            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
                            ProfileScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = "profile") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("sign_up_flow")
                            }
                            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
                            ProfileScreen1(navController = navController, viewModel = viewModel)
                        }
                        composable(route = "firstPage") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("sign_up_flow")
                            }
                            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
                            FirstPage(modifier = Modifier, navController, viewModel)
                        }
                        composable(route = "sign_up1") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("sign_up_flow")
                            }
                            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
                            Signup1(modifier = Modifier, navController, viewModel)
                        }
                        composable(route = "sign_up2") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("sign_up_flow")
                            }
                            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
                            Signup2(modifier = Modifier, navController, viewModel)
                        }
                        composable(route = "login") { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("sign_up_flow")
                            }
                            val viewModel: AuthViewModel = hiltViewModel(parentEntry)
                            LoginScreen(modifier = Modifier.fillMaxSize(), navController, viewModel)
                        }
                    }
            }
        }
    }
}
// @Composable
// fun EmptyScreen(message: String = "Coming Soon") {
//     Text(text = message)
// }
