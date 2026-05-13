package com.example.nearbyeats_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nearbyeats_app.domain.model.Restaurant
import com.example.nearbyeats_app.domain.usecase.GetRestaurantsUseCase
import com.example.nearbyeats_app.infrastructure.repository.RestaurantRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RestaurantViewModel(
    private val getRestaurantsUseCase: GetRestaurantsUseCase
) : ViewModel() {

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        _restaurants.value = getRestaurantsUseCase()
    }

    fun search(query: String) {
        _searchQuery.value = query
        _restaurants.value = getRestaurantsUseCase(query)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RestaurantViewModel(GetRestaurantsUseCase(RestaurantRepositoryImpl()))
            }
        }
    }
}
