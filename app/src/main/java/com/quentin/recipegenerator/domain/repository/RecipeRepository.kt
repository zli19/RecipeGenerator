package com.quentin.recipegenerator.domain.repository

import com.quentin.recipegenerator.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun insertRecipe(recipe: Recipe): Long
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun getRecipeById(id: Long): Recipe
    suspend fun upsertAllRecipes(recipes: List<Recipe>)

    suspend fun clear()
}