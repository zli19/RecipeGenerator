package com.quentin.recipegenerator.domain.service

import com.quentin.recipegenerator.domain.model.User

interface AuthenticationService {

    // Sign in a user with provided credentials
    suspend fun signIn(email: String, password: String): User?

    // Sign out a user
    suspend fun signOut()
}