package com.quentin.recipegenerator.domain.usecase

import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.service.AIService
import com.quentin.recipegenerator.domain.service.PictureService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

// Represent the use case of recipe generation
class GenerateRecipe(
    private val aiService: AIService,
    private val pictureService: PictureService,
) {
    // Use AIService to generate recipe and then get pictures for the recipe
    suspend operator fun invoke(
        requirements: String,
        preferences: List<String>,
        exclusions: List<String>
    ): Recipe? = withContext(Dispatchers.IO) {
        // Wait until generate a recipe with requirements and preferences from user input.
        val recipe = async {
            aiService.generateRecipe(requirements, preferences, exclusions)
        }.await()

        // Synchronously assign features and picture to generated recipe object.
        recipe?.let {
            it.preferences = preferences
            it.requirements = requirements
            it.picture = pictureService.fetchPicture(it.prompt!!)
        }
        return@withContext recipe
    }
}