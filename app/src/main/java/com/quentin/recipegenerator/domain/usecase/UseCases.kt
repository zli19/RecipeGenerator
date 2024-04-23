package com.quentin.recipegenerator.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton


// A data class encapsulate all use cases.
@Singleton
data class UseCases @Inject constructor(
    val generateRecipe: GenerateRecipe,
    val dataPersistence: DataPersistence,
    val userAuthentication: UserAuthentication
)