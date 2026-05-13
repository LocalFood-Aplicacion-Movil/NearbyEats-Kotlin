package com.example.nearbyeats_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nearbyeats_app.domain.model.Colleague
import com.example.nearbyeats_app.domain.usecase.GetColleaguesUseCase
import com.example.nearbyeats_app.infrastructure.repository.ColleagueRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ColleaguesViewModel(
    private val getColleaguesUseCase: GetColleaguesUseCase
) : ViewModel() {

    private val _colleagues = MutableStateFlow<List<Colleague>>(emptyList())
    val colleagues: StateFlow<List<Colleague>> = _colleagues

    init {
        _colleagues.value = getColleaguesUseCase()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ColleaguesViewModel(GetColleaguesUseCase(ColleagueRepositoryImpl()))
            }
        }
    }
}
