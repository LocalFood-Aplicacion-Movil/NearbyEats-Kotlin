package com.example.nearbyeats_app.domain.usecase

import com.example.nearbyeats_app.domain.service.AuthService

class LogoutUseCase(private val authService: AuthService) {
    operator fun invoke() = authService.logout()
}
