package com.example.nearbyeats_app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
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
import com.example.nearbyeats_app.presentation.viewmodel.HomeViewModel
import com.example.nearbyeats_app.ui.theme.*
import com.example.nearbyeats_app.utils.toStarDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val featured by homeViewModel.featuredRestaurants.collectAsState()

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
            // ── Top bar ───────────────────────────────────────────────────
            TopAppBar(
                title = {
                    Text(
                        text = "Inicio",
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    // Welcome card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = LogoBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "¡Bienvenido a NearbyEats!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrownAccent
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Descubre los mejores restaurantes cerca de ti y conecta con tus colegas.",
                                fontSize = 14.sp,
                                color = TextDark.copy(alpha = 0.75f),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Restaurantes Destacados",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = TextDark,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                items(featured) { restaurant ->
                    FeaturedRestaurantCard(restaurant = restaurant)
                }
            }
        }
    }
}

@Composable
private fun FeaturedRestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category badge circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(52.dp)
                    .background(PeachBackground, shape = RoundedCornerShape(50))
            ) {
                Text(
                    text = restaurant.category.take(2).uppercase(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = BrownAccent
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = restaurant.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = StarColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = restaurant.rating.toStarDisplay(),
                        fontSize = 13.sp,
                        color = TextDark.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• ${restaurant.category}",
                        fontSize = 13.sp,
                        color = TextDark.copy(alpha = 0.6f)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = BrownAccent.copy(alpha = 0.6f),
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = restaurant.address,
                        fontSize = 12.sp,
                        color = TextDark.copy(alpha = 0.55f)
                    )
                }
            }

            // Open/closed chip
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (restaurant.isOpen) StatusOnline.copy(alpha = 0.15f)
                else StatusOffline.copy(alpha = 0.15f)
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
    }
}
