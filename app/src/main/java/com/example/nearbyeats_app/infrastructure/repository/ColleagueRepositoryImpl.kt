package com.example.nearbyeats_app.infrastructure.repository

import com.example.nearbyeats_app.domain.model.Colleague
import com.example.nearbyeats_app.domain.model.ColleagueStatus
import com.example.nearbyeats_app.domain.repository.ColleagueRepository

class ColleagueRepositoryImpl : ColleagueRepository {

    private val colleagues = listOf(
        Colleague("1", "Ana García", ColleagueStatus.ONLINE, "A"),
        Colleague("2", "Carlos López", ColleagueStatus.OFFLINE, "C"),
        Colleague("3", "María Rodríguez", ColleagueStatus.ONLINE, "M"),
        Colleague("4", "Juan Martínez", ColleagueStatus.BUSY, "J"),
        Colleague("5", "Laura Sánchez", ColleagueStatus.ONLINE, "L"),
        Colleague("6", "Pedro Fernández", ColleagueStatus.OFFLINE, "P")
    )

    override fun getAll(): List<Colleague> = colleagues
    override fun findById(id: String): Colleague? = colleagues.find { it.id == id }
}
