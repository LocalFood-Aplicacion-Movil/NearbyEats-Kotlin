package com.example.nearbyeats_app.domain.service

import com.example.nearbyeats_app.domain.model.AuthSession

interface AuthService {
    suspend fun signIn(username: String, password: String): AuthSession
    suspend fun signUp(username: String, password: String): String
    fun logout()
    fun isLoggedIn(): Boolean
    fun currentSession(): AuthSession?
}
