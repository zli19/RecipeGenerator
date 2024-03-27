package com.quentin.recipegenerator.data.db

import android.content.Context
import com.quentin.recipegenerator.data.db.roomdb.RecipeDao
import com.quentin.recipegenerator.data.db.roomdb.RecipeDatabase
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.repository.RecipeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

class RoomRecipeRepo(context: Context) : RecipeRepository {

    private var recipeDao: RecipeDao = RecipeDatabase.getInstance(context).recipeDao()

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getRecipes()
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.upsertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}