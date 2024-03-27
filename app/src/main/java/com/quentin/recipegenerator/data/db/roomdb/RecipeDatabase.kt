package com.quentin.recipegenerator.data.db.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quentin.recipegenerator.domain.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipeConverters::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var instance: RecipeDatabase? = null

        fun getInstance(context: Context)=
            instance ?: synchronized(this){
                instance ?: databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipeGPT.db"
                ).build()
            }
    }
}