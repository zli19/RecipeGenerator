package com.quentin.recipegenerator.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val original: String,
    val landscape: String
)