package com.quentin.recipegenerator.domain.usecase

import com.quentin.recipegenerator.domain.model.User
import com.quentin.recipegenerator.domain.service.AuthenticationService

class UserAuthentication (
    private val authenticationService: AuthenticationService
){
    suspend fun signIn(email: String, password: String): User?{
        return authenticationService.signIn(email, password)
    }

    suspend fun signOut(){
        authenticationService.signOut()
    }
}