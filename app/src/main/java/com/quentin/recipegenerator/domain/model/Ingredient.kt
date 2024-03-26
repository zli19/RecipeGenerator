package com.quentin.recipegenerator.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val description: String,
    val nutrition: String
)