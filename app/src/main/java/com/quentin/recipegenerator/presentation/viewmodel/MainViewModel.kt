package com.quentin.recipegenerator.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.repository.RecipeRepository
import com.quentin.recipegenerator.domain.usecase.GenerateRecipe
import com.quentin.recipegenerator.presentation.view.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val generateRecipe: GenerateRecipe
): ViewModel(){
    
    var user by mutableStateOf("")
    var currentScreen by mutableStateOf<Destination>(Destination.AI)

    var recipeState by mutableStateOf(RecipeState())

    var recipeBook by mutableStateOf<List<Recipe>>(emptyList())

    init {
        // Retrieve data from database
        viewModelScope.launch {
            repository.getAllRecipes().collectLatest {
                recipeBook = it
            }
        }
    }


    // Handles the click event for the generate button
    fun onGenerateButtonClicked(
        input: String, navController: NavController
    ) = viewModelScope.launch {
            recipeState.input = input

            // Wait until recipe is ready then navigate automatically
            getRecipe(input).join()

            navController.navigate(Destination.Recipe.route)
            currentScreen = Destination.Recipe
    }

    fun getRecipeFromBook(recipe: Recipe, navController: NavController){
        recipeState.recipe = recipe
        recipeState.status = RecipeStatus.READY

        navController.navigate(Destination.Recipe.route)
        currentScreen = Destination.Recipe
    }

    // Asynchronously launch the AI service to start generating recipe and update the value to recipeState
    private fun getRecipe(ingredients: String) = viewModelScope.launch(Dispatchers.IO) {
        // Change recipeState status to GENERATING
        recipeState = recipeState.copy(
            status = RecipeStatus.GENERATING
        )

        // Start to generate recipe and assign it to recipeState
        generateRecipe(ingredients)?.let {
            recipeState.recipe = it
            repository.insertRecipe(it) // Insert to book for testing only
        }

        // Change recipeState status to READY
        recipeState = recipeState.copy(
            status = RecipeStatus.READY
        )
    }
}