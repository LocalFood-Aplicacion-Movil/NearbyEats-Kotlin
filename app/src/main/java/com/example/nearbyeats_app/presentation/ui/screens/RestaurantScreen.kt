package com.example.nearbyeats_app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nearbyeats_app.domain.model.Restaurant
import com.example.nearbyeats_app.presentation.viewmodel.RestaurantViewModel
import com.example.nearbyeats_app.ui.theme.*
import com.example.nearbyeats_app.utils.toStarDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    navController: NavController,
    viewModel: RestaurantViewModel = viewModel(factory = RestaurantViewModel.Factory)
) {
    val restaurants by viewModel.restaurants.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

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
                        text = "Restaurante",
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

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.search(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar restaurantes…", fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = BrownAccent)
                },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrownAccent,
                    unfocusedBorderColor = DividerColor,
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground
                )
            )

            Text(
                text = "${restaurants.size} restaurantes encontrados",
                fontSize = 13.sp,
                color = TextDark.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(restaurants) { restaurant ->
                    RestaurantCard(restaurant = restaurant)
                }
            }
        }
    }
}

@Composable
private fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = restaurant.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = PeachBackground.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = restaurant.category,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = BrownAccent,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (restaurant.isOpen) StatusOnline.copy(alpha = 0.12f)
                    else StatusOffline.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = if (restaurant.isOpen) "Abierto" else "Cerrado",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (restaurant.isOpen) StatusOnline else StatusOffline,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = DividerColor, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = BrownAccent.copy(0.7f), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = restaurant.address,
                        fontSize = 12.sp,
                        color = TextDark.copy(alpha = 0.6f),
                        modifier = Modifier.widthIn(max = 200.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = StarColor, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = restaurant.rating.toStarDisplay(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )
                }
            }
        }
    }
}
