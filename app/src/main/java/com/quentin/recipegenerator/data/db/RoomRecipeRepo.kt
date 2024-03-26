package com.quentin.recipegenerator.data.db

import android.content.Context
import com.quentin.recipegenerator.data.db.roomdb.RecipeDao
import com.quentin.recipegenerator.data.db.roomdb.RecipeDatabase
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RoomRecipeRepo(context: Context) : RecipeRepository {

    private var recipeDao: RecipeDao = RecipeDatabase.getInstance(context).recipeDao()

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getRecipesOrderByName()
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.upsertRecipeWithTimestamp(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.upsertRecipeWithTimestamp(recipe)
    }
}