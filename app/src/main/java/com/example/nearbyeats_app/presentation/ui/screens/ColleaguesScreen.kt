package com.example.nearbyeats_app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nearbyeats_app.domain.model.Colleague
import com.example.nearbyeats_app.domain.model.ColleagueStatus
import com.example.nearbyeats_app.presentation.viewmodel.ColleaguesViewModel
import com.example.nearbyeats_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColleaguesScreen(
    navController: NavController,
    viewModel: ColleaguesViewModel = viewModel(factory = ColleaguesViewModel.Factory)
) {
    val colleagues by viewModel.colleagues.collectAsState()

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
                        text = "Colegas",
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

            Text(
                text = "${colleagues.size} colegas conectados",
                fontSize = 13.sp,
                color = TextDark.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(colleagues) { colleague ->
                    ColleagueCard(colleague = colleague)
                }
            }
        }
    }
}

@Composable
private fun ColleagueCard(colleague: Colleague) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circle with initial
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .background(BrownAccent.copy(alpha = 0.15f), CircleShape)
            ) {
                Text(
                    text = colleague.avatarInitial,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrownAccent
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = colleague.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = when (colleague.status) {
                                    ColleagueStatus.ONLINE -> StatusOnline
                                    ColleagueStatus.BUSY -> StatusBusy
                                    ColleagueStatus.OFFLINE -> StatusOffline
                                },
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = colleague.status.label,
                        fontSize = 13.sp,
                        color = when (colleague.status) {
                            ColleagueStatus.ONLINE -> StatusOnline
                            ColleagueStatus.BUSY -> StatusBusy
                            ColleagueStatus.OFFLINE -> StatusOffline
                        }
                    )
                }
            }

            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BrownAccent),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                modifier = Modifier.height(34.dp)
            ) {
                Text(text = "Ver perfil", fontSize = 12.sp)
            }
        }
    }
}
