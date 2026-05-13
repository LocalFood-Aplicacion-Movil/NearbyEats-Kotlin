package com.example.nearbyeats_app.presentation.navigation

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Home : Screen("home")
    object Colleagues : Screen("colleagues")
    object Restaurant : Screen("restaurant")
    object Calculator : Screen("calculator")
}
