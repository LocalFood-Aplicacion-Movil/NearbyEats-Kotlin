package com.example.nearbyeats_app.domain.repository

import com.example.nearbyeats_app.domain.model.Restaurant

interface RestaurantRepository {
    fun getAll(): List<Restaurant>
    fun findById(id: String): Restaurant?
    fun searchByName(query: String): List<Restaurant>
}
