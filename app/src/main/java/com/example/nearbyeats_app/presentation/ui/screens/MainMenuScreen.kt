package com.example.nearbyeats_app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nearbyeats_app.presentation.navigation.Screen
import com.example.nearbyeats_app.ui.theme.*

@Composable
fun MainMenuScreen(navController: NavController) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Deseas cerrar tu sesión?") },
            confirmButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Sí", color = LogoutRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PeachBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── Header row ──────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir menú",
                    tint = Color.Black,
                    modifier = Modifier.size(34.dp)
                )
            }

            // ── Circular logo ────────────────────────────────────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(LogoBackground)
                    .border(5.dp, Color.White.copy(alpha = 0.85f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Groups,
                    contentDescription = "NearbyEats",
                    tint = BrownAccent,
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Navigation items ─────────────────────────────────────────
            MenuNavItem(
                icon = Icons.Default.Home,
                label = "Inicio",
                isActive = true,
                onClick = { navController.navigate(Screen.Home.route) }
            )

            Spacer(modifier = Modifier.height(4.dp))

            MenuNavItem(
                icon = Icons.Default.Groups,
                label = "Colegas",
                isActive = false,
                onClick = { navController.navigate(Screen.Colleagues.route) }
            )

            Spacer(modifier = Modifier.height(4.dp))

            MenuNavItem(
                icon = Icons.Default.Storefront,
                label = "Restaurante",
                isActive = false,
                onClick = { navController.navigate(Screen.Restaurant.route) }
            )

            Spacer(modifier = Modifier.height(4.dp))

            MenuNavItem(
                icon = Icons.Default.Calculate,
                label = "Calculo",
                isActive = false,
                onClick = { navController.navigate(Screen.Calculator.route) }
            )

            Spacer(modifier = Modifier.weight(1f))

            // ── Logout button ────────────────────────────────────────────
            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .padding(bottom = 36.dp)
                    .width(200.dp)
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LogoutRed),
                shape = RoundedCornerShape(6.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Cerrar Sesion",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
private fun MenuNavItem(
    icon: ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isActive) MenuHighlight else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 28.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Black,
            modifier = Modifier.size(54.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = label,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            letterSpacing = 0.5.sp
        )
    }
}
