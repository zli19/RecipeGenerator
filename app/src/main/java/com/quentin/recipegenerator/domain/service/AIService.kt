package com.quentin.recipegenerator.domain.service

import com.quentin.recipegenerator.domain.model.Recipe

// Represent a AI service that can generate a recipe
interface AIService {
    // Generate a recipe with requirements and preferences from user input.
    suspend fun generateRecipe(requirements: String, preferences: List<String>): Recipe?

    // Require that the newly generated recipe is using the same cached requirements and preferences
    // but will be different than previous generated ones,
    // meaning internally the AIService must have cache capability.
    suspend fun regenerateRecipe(): Recipe?
}