package com.quentin.recipegenerator.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Serving(
    val number: Int,
    val nutritionPerServing: String
)