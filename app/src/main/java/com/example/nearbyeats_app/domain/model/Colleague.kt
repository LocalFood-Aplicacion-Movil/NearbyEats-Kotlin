package com.example.nearbyeats_app.domain.model

data class Colleague(
    val id: String,
    val name: String,
    val status: ColleagueStatus,
    val avatarInitial: String
)

enum class ColleagueStatus(val label: String) {
    ONLINE("En línea"),
    OFFLINE("Fuera de línea"),
    BUSY("Ocupado")
}
