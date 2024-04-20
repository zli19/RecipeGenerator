package com.quentin.recipegenerator.domain.repository

import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.model.User

interface RemoteRecipeRepository {
    suspend fun getAllRecipes(user: User): List<Recipe>

    suspend fun insertRecipe(recipe: Recipe, user: User)
    suspend fun deleteRecipe(recipe: Recipe, user: User)
}