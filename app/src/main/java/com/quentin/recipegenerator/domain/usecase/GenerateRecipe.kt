package com.quentin.recipegenerator.domain.usecase

import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.service.AIService
import com.quentin.recipegenerator.domain.service.PictureService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GenerateRecipe @Inject constructor(
    private val aiService: AIService,
    private val pictureService: PictureService,
) {

//    private val ioScope = CoroutineScope(Job() + Dispatchers.IO)
//    private var recipe: Recipe? = null

    // Use AIService to generate recipe and then get pictures for the recipe
    suspend operator fun invoke(vararg ingredients: String): Recipe? {
        return withContext(Dispatchers.IO) {
            val recipe = if (ingredients.isEmpty()) {
                async {
                    aiService.regenerateRecipe()
                }.await()
            } else {
                async {
                    aiService.generateRecipe(ingredients[0])
                }.await()
            }
            recipe?.let {
                it.pictures = pictureService.fetchPictures(it.name)
            }
            return@withContext recipe
        }
    }
}