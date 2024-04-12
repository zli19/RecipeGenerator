package com.quentin.recipegenerator.presentation.viewmodel

import com.quentin.recipegenerator.domain.model.Recipe


// Used for ViewModel to store recipe data and reflect changes of data
data class RecipeState(
//    var requirements: String = "",
//    var preferences: List<String> = emptyList(),
    var status: RecipeStatus = RecipeStatus.EMPTY,
    var recipe: Recipe = Recipe(),
    var history: MutableList<String> = mutableListOf()
)