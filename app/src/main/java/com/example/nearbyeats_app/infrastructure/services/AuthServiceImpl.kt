package com.example.nearbyeats_app.infrastructure.services

import com.example.nearbyeats_app.domain.service.AuthService

class AuthServiceImpl : AuthService {
    private var loggedIn = true

    override fun logout() { loggedIn = false }
    override fun isLoggedIn(): Boolean = loggedIn
}
