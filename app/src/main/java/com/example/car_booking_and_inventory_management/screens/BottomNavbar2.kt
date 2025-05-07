package com.example.car_booking_and_inventory_management.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.room.parser.Section
import com.example.car_booking_and_inventory_management.data.BottomNavItem

var bottomNavs=listOf(
    BottomNavItem(
        title = "Dashboard",
        route="dashboard",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Booking",
        route="booking",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Users",
        route="users",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Cars",
        route="cars",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        hasNews = false,
        badges = 0
    )
)

@Composable
fun BottomNavbar2(modifier: Modifier = Modifier, navController: NavController) {
    var selected by remember { mutableIntStateOf(0) }
    NavigationBar {
        bottomNavs.forEachIndexed{index, bottomNav->
            NavigationBarItem(
                selected=index==selected,
                onClick={
                    selected=index
                    navController.navigate(bottomNav.route)
                },
                icon={
                    BadgedBox(
                        badge= {
                            when{
                                bottomNav.badges!=0->{
                                    Badge{ Section.Text(bottomNav.badges.toString()) }
                                }
                                bottomNav.hasNews==true->{
                                    Badge()
                                }
                                else->{}
                            }
                        }

                    ) {
                        Icon(
                            imageVector = if(selected==index){
                                bottomNav.selectedIcon
                            }
                            else{
                                bottomNav.unselectedIcon
                            },
                            contentDescription = bottomNav.title
                        )
                    }
                }
            )
        }
    }

}