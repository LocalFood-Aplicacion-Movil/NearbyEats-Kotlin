package com.example.nearbyeats_app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nearbyeats_app.presentation.viewmodel.CalculatorViewModel
import com.example.nearbyeats_app.ui.theme.*
import com.example.nearbyeats_app.utils.toCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    navController: NavController,
    viewModel: CalculatorViewModel = viewModel()
) {
    val amount by viewModel.amount.collectAsState()
    val tipPercent by viewModel.tipPercent.collectAsState()
    val people by viewModel.people.collectAsState()
    val tipAmount by viewModel.tipAmount.collectAsState()
    val totalWithTip by viewModel.totalWithTip.collectAsState()
    val perPerson by viewModel.perPerson.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PeachBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Calculo",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 24.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PeachBackground,
                    titleContentColor = TextDark,
                    navigationIconContentColor = TextDark
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // ── Inputs card ───────────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Ingresa los datos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = BrownAccent
                        )

                        CalcInputField(
                            label = "Monto total (€)",
                            value = amount,
                            onValueChange = { viewModel.onAmountChanged(it) },
                            placeholder = "0.00"
                        )

                        CalcInputField(
                            label = "% Propina",
                            value = tipPercent,
                            onValueChange = { viewModel.onTipPercentChanged(it) },
                            placeholder = "10"
                        )

                        CalcInputField(
                            label = "Número de personas",
                            value = people,
                            onValueChange = { viewModel.onPeopleChanged(it) },
                            placeholder = "1"
                        )
                    }
                }

                // ── Results card ──────────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LogoBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Resultados",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = BrownAccent
                        )

                        ResultRow(label = "Propina:", value = tipAmount.toCurrency())

                        Divider(color = DividerColor, thickness = 0.8.dp)

                        ResultRow(
                            label = "Total con propina:",
                            value = totalWithTip.toCurrency(),
                            isHighlighted = true
                        )

                        Divider(color = DividerColor, thickness = 0.8.dp)

                        ResultRow(
                            label = "Por persona:",
                            value = perPerson.toCurrency(),
                            isHighlighted = true
                        )
                    }
                }

                // ── Quick tip buttons ─────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Propina rápida",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = BrownAccent
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("5", "10", "15", "20").forEach { pct ->
                                OutlinedButton(
                                    onClick = { viewModel.onTipPercentChanged(pct) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (tipPercent == pct)
                                            BrownAccent.copy(alpha = 0.1f) else Color.Transparent,
                                        contentColor = BrownAccent
                                    ),
                                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                                ) {
                                    Text(text = "$pct%", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalcInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        placeholder = { Text(placeholder, color = TextDark.copy(alpha = 0.4f)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrownAccent,
            unfocusedBorderColor = DividerColor,
            focusedLabelColor = BrownAccent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Composable
private fun ResultRow(label: String, value: String, isHighlighted: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = if (isHighlighted) 15.sp else 14.sp,
            fontWeight = if (isHighlighted) FontWeight.SemiBold else FontWeight.Normal,
            color = TextDark.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            fontSize = if (isHighlighted) 18.sp else 15.sp,
            fontWeight = if (isHighlighted) FontWeight.ExtraBold else FontWeight.Medium,
            color = if (isHighlighted) BrownAccent else TextDark
        )
    }
}
