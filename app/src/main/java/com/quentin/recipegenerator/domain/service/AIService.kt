package com.quentin.recipegenerator.domain.service

import com.quentin.recipegenerator.domain.model.Recipe

// Represent a AI service that can generate a recipe
interface AIService {
    // Generate a recipe with requirements and preferences from user input.
    suspend fun generateRecipe(
        requirements: String,
        preferences: List<String>,
        exclusions: List<String>
    ): Recipe?
}