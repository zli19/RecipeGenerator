package com.quentin.recipegenerator.data.db

import android.content.Context
import com.quentin.recipegenerator.data.db.roomdb.RecipeDao
import com.quentin.recipegenerator.data.db.roomdb.RecipeDatabase
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.repository.LocalRecipeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomLocalRecipeRepo @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalRecipeRepository {

    private var recipeDao: RecipeDao = RecipeDatabase.getInstance(context).recipeDao()

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getRecipes()
    }

    override suspend fun insertRecipe(recipe: Recipe): Long {
        return recipeDao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    override suspend fun getRecipeById(id: Long): Recipe {
        return recipeDao.getRecipe(id)
    }

    override suspend fun upsertAllRecipes(recipes: List<Recipe>) {
        recipeDao.upsertAllRecipes(recipes)
    }

    override suspend fun clear() {
        recipeDao.clear()
    }
}