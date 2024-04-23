package com.quentin.recipegenerator.domain.usecase

import com.quentin.recipegenerator.domain.model.User
import com.quentin.recipegenerator.domain.service.AuthenticationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAuthentication @Inject constructor(
    private val authenticationService: AuthenticationService
){
    suspend fun signIn(email: String, password: String): User?{
        return authenticationService.signIn(email, password)
    }

    suspend fun signOut(){
        authenticationService.signOut()
    }
}