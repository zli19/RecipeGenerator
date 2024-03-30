package com.quentin.recipegenerator.presentation.viewmodel

import com.quentin.recipegenerator.domain.model.Recipe

data class RecipeState(
    var requirements: String = "",
    var status: RecipeStatus = RecipeStatus.EMPTY,
    var recipe: Recipe? = null,
)