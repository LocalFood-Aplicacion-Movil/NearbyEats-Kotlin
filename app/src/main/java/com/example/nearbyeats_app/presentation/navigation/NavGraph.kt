package com.example.nearbyeats_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nearbyeats_app.presentation.ui.screens.CalculatorScreen
import com.example.nearbyeats_app.presentation.ui.screens.ColleaguesScreen
import com.example.nearbyeats_app.presentation.ui.screens.HomeScreen
import com.example.nearbyeats_app.presentation.ui.screens.MainMenuScreen
import com.example.nearbyeats_app.presentation.ui.screens.RestaurantScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Menu.route) {
            MainMenuScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Colleagues.route) {
            ColleaguesScreen(navController = navController)
        }
        composable(Screen.Restaurant.route) {
            RestaurantScreen(navController = navController)
        }
        composable(Screen.Calculator.route) {
            CalculatorScreen(navController = navController)
        }
    }
}
