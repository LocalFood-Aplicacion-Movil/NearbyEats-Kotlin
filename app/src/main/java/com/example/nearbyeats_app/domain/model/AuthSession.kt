package com.example.nearbyeats_app.domain.model

data class AuthSession(
    val id: Int,
    val username: String,
    val token: String
)
