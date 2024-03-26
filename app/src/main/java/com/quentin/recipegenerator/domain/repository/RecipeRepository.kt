package com.quentin.recipegenerator.domain.repository

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe1>>
    suspend fun insertRecipe(recipe: Recipe1)
    suspend fun deleteRecipe(recipe: Recipe1)
    suspend fun updateRecipe(recipe: Recipe1)
}