package com.example.nearbyeats_app.application.dto

data class RestaurantDto(
    val id: String,
    val name: String,
    val address: String,
    val category: String,
    val rating: Float,
    val isOpen: Boolean
)
