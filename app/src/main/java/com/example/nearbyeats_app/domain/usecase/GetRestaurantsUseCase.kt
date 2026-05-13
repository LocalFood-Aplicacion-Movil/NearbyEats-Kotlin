package com.example.nearbyeats_app.domain.usecase

import com.example.nearbyeats_app.domain.model.Restaurant
import com.example.nearbyeats_app.domain.repository.RestaurantRepository

class GetRestaurantsUseCase(private val repository: RestaurantRepository) {
    operator fun invoke(query: String = ""): List<Restaurant> =
        if (query.isBlank()) repository.getAll()
        else repository.searchByName(query)
}
