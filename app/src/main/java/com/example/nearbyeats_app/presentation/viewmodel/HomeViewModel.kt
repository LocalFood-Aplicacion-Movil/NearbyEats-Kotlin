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

class HomeViewModel(
    private val getRestaurantsUseCase: GetRestaurantsUseCase
) : ViewModel() {

    private val _featuredRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val featuredRestaurants: StateFlow<List<Restaurant>> = _featuredRestaurants

    init {
        _featuredRestaurants.value = getRestaurantsUseCase().take(3)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(GetRestaurantsUseCase(RestaurantRepositoryImpl()))
            }
        }
    }
}
