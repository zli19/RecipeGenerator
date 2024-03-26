package com.quentin.recipegenerator.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val small: String,
    val landscape: String
)

