package com.example.nearbyeats_app.domain.repository

import com.example.nearbyeats_app.domain.model.Colleague

interface ColleagueRepository {
    fun getAll(): List<Colleague>
    fun findById(id: String): Colleague?
}
