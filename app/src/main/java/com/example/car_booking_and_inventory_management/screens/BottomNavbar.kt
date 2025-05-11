package com.example.car_booking_and_inventory_management.screens

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.car_booking_and_inventory_management.data.BottomNavItem


var bottomNavItems=listOf(
    BottomNavItem(
        title = "Home",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon =Icons.Outlined.Home ,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "History",
        route = "history",
        selectedIcon = Icons.Filled.Refresh,
        unselectedIcon =Icons.Outlined.Refresh ,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Profile",
        route = "profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon =Icons.Outlined.AccountCircle ,
        hasNews = false,
        badges = 0
    )
)
@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val selectedIndex = bottomNavItems.indexOfFirst { it.route == currentRoute }.takeIf { it != -1 } ?: 0

    NavigationBar {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    if (currentRoute != bottomNavItem.route) {
                        navController.navigate(bottomNavItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {
                            when {
                                bottomNavItem.badges != 0 -> {
                                    Badge {
                                        Text(bottomNavItem.badges.toString())
                                    }
                                }
                                bottomNavItem.hasNews -> {
                                    Badge()
                                }
                                else -> {
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selectedIndex == index) {
                                bottomNavItem.selectedIcon
                            } else {
                                bottomNavItem.unselectedIcon
                            },
                            contentDescription = bottomNavItem.title
                        )
                    }
                },
                label = {
                    Text(
                        text = bottomNavItem.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}
