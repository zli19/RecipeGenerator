package com.quentin.recipegenerator.domain.model

data class RecipeState(
    var input: String = "",
    val status: RecipeStatus = RecipeStatus.EMPTY,
    var recipe: Recipe? = null,
)