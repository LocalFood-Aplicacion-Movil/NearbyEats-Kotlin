package com.example.nearbyeats_app.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nearbyeats_app.presentation.navigation.NavGraph
import com.example.nearbyeats_app.ui.theme.NearbyEats_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyEats_AppTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
