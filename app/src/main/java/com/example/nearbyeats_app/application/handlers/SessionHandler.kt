package com.example.nearbyeats_app.application.handlers

import com.example.nearbyeats_app.domain.service.AuthService

class SessionHandler(private val authService: AuthService) {
    fun endSession() = authService.logout()
    fun isActive(): Boolean = authService.isLoggedIn()
}
