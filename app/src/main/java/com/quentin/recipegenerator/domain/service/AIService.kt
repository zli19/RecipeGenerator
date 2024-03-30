package com.quentin.recipegenerator.domain.service

import com.quentin.recipegenerator.domain.model.Recipe

interface AIService {
    // Generate a recipe with ingredients from input.
    suspend fun generateRecipe(ingredients: String, features: List<String>): Recipe?

    // Require that the newly generated recipe is using the same cached ingredients
    // but will be different than previous generated ones,
    // meaning internally the AIService must have cache capability.
    suspend fun regenerateRecipe(): Recipe?
}