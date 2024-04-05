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
@Singleton
class GenerateRecipe @Inject constructor(
    private val aiService: AIService,
    private val pictureService: PictureService,
) {

    // Use AIService to generate recipe and then get pictures for the recipe
    suspend operator fun invoke(requirements: String? = null, preferences: List<String> = emptyList()): Recipe? {
        return withContext(Dispatchers.IO) {
            val recipe = if (requirements == null) {
                // Wait until use the same cached requirements and preferences to regenerate a different recipe,
                async {
                    aiService.regenerateRecipe()
                }.await()
            } else {
                // Wait until generate a recipe with requirements and preferences from user input.
                async {
                    aiService.generateRecipe(requirements, preferences)
                }.await()
            }
            // Synchronously assign features and picture to generated recipe object.
            recipe?.let {
                it.features = preferences
                it.picture = pictureService.fetchPicture(it.prompt)
            }
            return@withContext recipe
        }
    }
}