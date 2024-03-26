package com.quentin.recipegenerator.presentation.viewmodel

data class RecipeState(
    var input: String = "",
    val status: RecipeStatus = RecipeStatus.EMPTY,
    var recipe: Recipe1? = null,
)