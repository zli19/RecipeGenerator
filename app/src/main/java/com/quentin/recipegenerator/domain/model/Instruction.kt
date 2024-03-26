package com.quentin.recipegenerator.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Instruction(
    val description: String,
    val title: String
)