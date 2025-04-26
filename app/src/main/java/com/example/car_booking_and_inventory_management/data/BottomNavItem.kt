package com.example.car_booking_and_inventory_management.data

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title:String,
    val route:String,
    val selectedIcon: ImageVector,
    val unselectedIcon:ImageVector,
    val hasNews:Boolean,
    val badges:Int?
)

