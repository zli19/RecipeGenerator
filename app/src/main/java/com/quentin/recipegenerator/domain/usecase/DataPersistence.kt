package com.quentin.recipegenerator.domain.usecase

import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.model.User
import com.quentin.recipegenerator.domain.repository.LocalRecipeRepository
import com.quentin.recipegenerator.domain.repository.RemoteRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

// Represent use cases related to data persistence
@Singleton
class DataPersistence @Inject constructor(
    private val localRecipeRepository: LocalRecipeRepository,
    private val remoteRecipeRepository: RemoteRecipeRepository
) {

    // Get all recipes from local repository
    fun getAllLocalRecipes(): Flow<List<Recipe>> {
        return localRecipeRepository.getAllRecipes()
    }

    // Synchronize local recipe repository with remote recipe repository
    suspend fun syncLocalDataFromRemote(
        user: User,
        hasUserChanged: Boolean
    ) = withContext(Dispatchers.IO){
        remoteRecipeRepository.getAllRecipes(user).apply {
            if(hasUserChanged){
                async {
                    localRecipeRepository.clear()
                }.await()
            }
            localRecipeRepository.upsertAllRecipes(this)
        }
    }

    // Save a recipe to both local and remote repository
    suspend fun saveRecipe(
        recipe: Recipe,
        user: User
    ): Recipe = withContext(Dispatchers.IO){
        val id = localRecipeRepository.insertRecipe(recipe)
        val storedRecipe = localRecipeRepository.getRecipeById(id)
        remoteRecipeRepository.insertRecipe(storedRecipe, user)
        return@withContext storedRecipe
    }

    // delete a recipe from both local and remote repository
    suspend fun deleteRecipe(
        recipe: Recipe,
        user: User
    ) = withContext(Dispatchers.IO){
        localRecipeRepository.deleteRecipe(recipe)
        remoteRecipeRepository.deleteRecipe(recipe, user)
    }
}