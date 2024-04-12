package com.quentin.recipegenerator.data.db.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quentin.recipegenerator.domain.model.Recipe
import kotlinx.coroutines.flow.Flow


@Dao
abstract class RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipe(recipe: Recipe): Long

    @Delete
    abstract suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes order by id DESC")
    abstract fun getRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    abstract suspend fun getRecipe(id: Long): Recipe

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