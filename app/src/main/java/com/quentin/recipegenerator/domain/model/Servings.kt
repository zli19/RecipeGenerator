package com.quentin.recipegenerator.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Servings(
    val number: String,
    val nutritionPerServing: String
)