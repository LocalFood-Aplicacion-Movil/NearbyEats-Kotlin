package com.example.nearbyeats_app.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    val category: String,
    val rating: Float,
    val isOpen: Boolean = true
)
