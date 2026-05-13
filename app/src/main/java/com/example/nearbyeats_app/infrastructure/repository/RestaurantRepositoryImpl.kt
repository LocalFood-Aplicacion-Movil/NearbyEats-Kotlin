package com.example.nearbyeats_app.infrastructure.repository

import com.example.nearbyeats_app.domain.model.Restaurant
import com.example.nearbyeats_app.domain.repository.RestaurantRepository

class RestaurantRepositoryImpl : RestaurantRepository {

    private val restaurants = listOf(
        Restaurant("1", "La Parrilla del Chef", "Calle Mayor 15, Madrid", "Parrilla", 4.5f, true),
        Restaurant("2", "Sushi Osaka", "Av. Diagonal 42, Barcelona", "Japonesa", 4.8f, true),
        Restaurant("3", "Pizza Napoli", "Gran Vía 88, Madrid", "Italiana", 4.2f, false),
        Restaurant("4", "Tacos El Bueno", "Calle Serrano 7, Sevilla", "Mexicana", 4.6f, true),
        Restaurant("5", "La Marisquería", "Paseo Marítimo 3, Valencia", "Mariscos", 4.4f, true),
        Restaurant("6", "Burger Station", "Calle Fuencarral 22, Madrid", "Hamburguesería", 4.0f, true)
    )

    override fun getAll(): List<Restaurant> = restaurants

    override fun findById(id: String): Restaurant? = restaurants.find { it.id == id }

    override fun searchByName(query: String): List<Restaurant> =
        restaurants.filter { it.name.contains(query, ignoreCase = true) }
}
