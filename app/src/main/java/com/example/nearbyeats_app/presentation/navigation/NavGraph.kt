package com.example.nearbyeats_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nearbyeats_app.infrastructure.services.AuthServiceImpl
import com.example.nearbyeats_app.presentation.ui.screens.AuthGateScreen
import com.example.nearbyeats_app.presentation.ui.screens.CalculatorScreen
import com.example.nearbyeats_app.presentation.ui.screens.ColleaguesScreen
import com.example.nearbyeats_app.presentation.ui.screens.LoginScreen
import com.example.nearbyeats_app.presentation.ui.screens.HomeScreen
import com.example.nearbyeats_app.presentation.ui.screens.MainMenuScreen
import com.example.nearbyeats_app.presentation.ui.screens.RestaurantScreen
import com.example.nearbyeats_app.presentation.ui.screens.SignUpScreen

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current.applicationContext
    val authService = remember(context) { AuthServiceImpl(context) }

    NavHost(
        navController = navController,
        startDestination = Screen.AuthGate.route
    ) {
        composable(Screen.AuthGate.route) {
            AuthGateScreen(navController = navController, authService = authService)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, authService = authService)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController, authService = authService)
        }
        composable(Screen.Menu.route) {
            MainMenuScreen(navController = navController, authService = authService)
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
