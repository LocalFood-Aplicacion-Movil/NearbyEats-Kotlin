package com.example.nearbyeats_app.domain.usecase

import com.example.nearbyeats_app.domain.model.Colleague
import com.example.nearbyeats_app.domain.repository.ColleagueRepository

class GetColleaguesUseCase(private val repository: ColleagueRepository) {
    operator fun invoke(): List<Colleague> = repository.getAll()
}
