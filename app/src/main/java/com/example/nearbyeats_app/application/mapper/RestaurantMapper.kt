package com.example.nearbyeats_app.application.mapper

import com.example.nearbyeats_app.application.dto.RestaurantDto
import com.example.nearbyeats_app.domain.model.Restaurant

object RestaurantMapper {
    fun toDto(restaurant: Restaurant): RestaurantDto = RestaurantDto(
        id = restaurant.id,
        name = restaurant.name,
        address = restaurant.address,
        category = restaurant.category,
        rating = restaurant.rating,
        isOpen = restaurant.isOpen
    )
}
