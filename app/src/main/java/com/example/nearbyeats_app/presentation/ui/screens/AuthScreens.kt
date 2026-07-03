package com.example.nearbyeats_app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nearbyeats_app.domain.service.AuthService
import com.example.nearbyeats_app.presentation.navigation.Screen
import com.example.nearbyeats_app.ui.theme.BrownAccent
import com.example.nearbyeats_app.ui.theme.CardBackground
import com.example.nearbyeats_app.ui.theme.PeachBackground
import com.example.nearbyeats_app.ui.theme.TextDark
import kotlinx.coroutines.launch

@Composable
fun AuthGateScreen(
    navController: NavController,
    authService: AuthService
) {
    LaunchedEffect(Unit) {
        val destination = if (authService.isLoggedIn()) Screen.Menu.route else Screen.Login.route
        navController.navigate(destination) {
            popUpTo(Screen.AuthGate.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    AuthLoadingScreen()
}

@Composable
fun LoginScreen(
    navController: NavController,
    authService: AuthService
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    AuthFormScreen(
        title = "Iniciar sesión",
        subtitle = "Usa tu usuario de NearbyEats para entrar a la app.",
        primaryButtonText = "Entrar",
        primaryIcon = Icons.Default.Login,
        username = username,
        password = password,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onUsernameChange = { username = it },
        onPasswordChange = { password = it },
        onPrimaryAction = {
            isLoading = true
            errorMessage = null
            scope.launch {
                try {
                    authService.signIn(username.trim(), password)
                    navController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                } catch (exception: Exception) {
                    errorMessage = exception.message ?: "No se pudo iniciar sesión"
                } finally {
                    isLoading = false
                }
            }
        },
        footerText = "¿No tienes cuenta? Regístrate",
        footerActionText = "Crear cuenta",
        onFooterAction = {
            navController.navigate(Screen.SignUp.route)
        }
    )
}

@Composable
fun SignUpScreen(
    navController: NavController,
    authService: AuthService
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    AuthFormScreen(
        title = "Crear cuenta",
        subtitle = "Regístrate para después iniciar sesión con JWT.",
        primaryButtonText = "Registrar",
        primaryIcon = Icons.Default.PersonAdd,
        username = username,
        password = password,
        isLoading = isLoading,
        errorMessage = errorMessage ?: successMessage,
        onUsernameChange = {
            username = it
            successMessage = null
            errorMessage = null
        },
        onPasswordChange = {
            password = it
            successMessage = null
            errorMessage = null
        },
        onPrimaryAction = {
            isLoading = true
            successMessage = null
            errorMessage = null
            scope.launch {
                try {
                    val message = authService.signUp(username.trim(), password)
                    successMessage = message
                    navController.popBackStack()
                } catch (exception: Exception) {
                    errorMessage = exception.message ?: "No se pudo registrar el usuario"
                } finally {
                    isLoading = false
                }
            }
        },
        footerText = "¿Ya tienes cuenta?",
        footerActionText = "Volver al login",
        onFooterAction = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun AuthLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PeachBackground),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = BrownAccent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthFormScreen(
    title: String,
    subtitle: String,
    primaryButtonText: String,
    primaryIcon: androidx.compose.ui.graphics.vector.ImageVector,
    username: String,
    password: String,
    isLoading: Boolean,
    errorMessage: String?,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPrimaryAction: () -> Unit,
    footerText: String,
    footerActionText: String,
    onFooterAction: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PeachBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NearbyEats",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif,
                color = BrownAccent
            )

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = subtitle,
                        color = TextDark.copy(alpha = 0.72f),
                        fontSize = 14.sp
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = onUsernameChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Usuario") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrownAccent,
                            focusedLabelColor = BrownAccent
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Contraseña") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrownAccent,
                            focusedLabelColor = BrownAccent
                        )
                    )

                    if (!errorMessage.isNullOrBlank()) {
                        Text(
                            text = errorMessage,
                            color = Color(0xFFB00020),
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = onPrimaryAction,
                        enabled = !isLoading && username.isNotBlank() && password.isNotBlank(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrownAccent)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Icon(primaryIcon, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(primaryButtonText, fontSize = 15.sp)
                        }
                    }

                    TextButton(onClick = onFooterAction, enabled = !isLoading) {
                        Row {
                            Text(footerText)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = footerActionText,
                                color = BrownAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
