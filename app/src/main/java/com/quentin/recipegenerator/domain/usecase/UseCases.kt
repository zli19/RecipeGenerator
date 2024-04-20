package com.quentin.recipegenerator.domain.usecase

data class UseCases(
    val generateRecipe: GenerateRecipe,
    val dataPersistence: DataPersistence,
    val userAuthentication: UserAuthentication
)