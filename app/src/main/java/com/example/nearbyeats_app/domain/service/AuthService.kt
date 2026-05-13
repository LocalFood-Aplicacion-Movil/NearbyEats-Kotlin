package com.example.nearbyeats_app.domain.service

interface AuthService {
    fun logout()
    fun isLoggedIn(): Boolean
}
