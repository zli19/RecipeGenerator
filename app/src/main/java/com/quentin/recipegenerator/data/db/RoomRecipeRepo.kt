package com.quentin.recipegenerator.data.db

import android.content.Context
import com.quentin.recipegenerator.data.db.roomdb.RecipeDao
import com.quentin.recipegenerator.data.db.roomdb.RecipeDatabase
import com.quentin.recipegenerator.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RoomRecipeRepo(context: Context) : RecipeRepository {

    private var recipeDao: RecipeDao = RecipeDatabase.getInstance(context).recipeDao()

    override fun getAllRecipes(): Flow<List<Recipe1>> {
        return recipeDao.getRecipesOrderByName()
    }

    override suspend fun insertRecipe(recipe: Recipe1) {
        recipeDao.upsertRecipeWithTimestamp(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe1) {
        recipeDao.deleteRecipe(recipe)
    }

    override suspend fun updateRecipe(recipe: Recipe1) {
        recipeDao.upsertRecipeWithTimestamp(recipe)
    }
}