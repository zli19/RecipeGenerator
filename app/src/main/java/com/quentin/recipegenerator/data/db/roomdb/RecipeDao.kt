package com.quentin.recipegenerator.data.db.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.quentin.recipegenerator.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Dao
abstract class RecipeDao {
    @Upsert
    abstract suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    abstract suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    abstract fun getRecipes(): Flow<List<Recipe>>

//    @Query("SELECT * FROM recipes ORDER BY time DESC")
//    abstract fun getRecipesOrderByTime(): Flow<List<Recipe>>
//    @Query("SELECT * FROM recipes ORDER BY name ASC")
//    abstract fun getRecipesOrderByName(): Flow<List<Recipe>>


//    suspend fun upsertRecipeWithTimestamp(recipe: Recipe){
//        upsertRecipe(recipe.apply {
//            time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
//        })
//    }
}