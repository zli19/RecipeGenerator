package com.quentin.recipegenerator.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
    var featureMap = mutableStateMapOf<String, Int>()

    init {
        // Retrieve data from database
        viewModelScope.launch {
            repository.getAllRecipes().collectLatest { recipes ->
                recipeBook = recipes
                val map = HashMap<String, Int>()
                for (feature in recipeBook.flatMap { it.features }) {
                    map[feature] = map.getOrDefault(feature, 0) + 1
                }
                featureMap.putAll(map.toList().sortedBy { -it.second }.toMap())
            }
        }
    }


    // Handles the click event for the generate button
    fun handleGenerateButtonClickEvent(
        requirements: String,
        preferences: List<String>,
        navController: NavController
    ) = viewModelScope.launch {
            recipeState.requirements = requirements
            recipeState.preferences = preferences

            // Wait until recipe is ready then navigate automatically
            getRecipe(requirements, preferences).join()

            navController.navigate(Destination.Recipe.route)
            currentScreen = Destination.Recipe
    }

    // Handles the click event for the recipe card
    fun handleRecipeCardClickEvent(recipe: Recipe, navController: NavController){
        recipeState.recipe = recipe
        recipeState.status = RecipeStatus.READY

        navController.navigate(Destination.Recipe.route)
        currentScreen = Destination.Recipe
    }

    // Handles the click event for the regenerate button
    fun handleRegenerateButtonClickEvent(
        navController: NavController
    ) = viewModelScope.launch {

        // Wait until recipe is ready then navigate automatically
        getRecipe().join()

        navController.navigate(Destination.Recipe.route)
        currentScreen = Destination.Recipe
    }

    // Asynchronously launch the AI service to start generating recipe and update the value to recipeState
    private fun getRecipe(requirements: String? = null, preferences: List<String> = emptyList()) = viewModelScope.launch(Dispatchers.IO) {
        // Change recipeState status to GENERATING
        recipeState = recipeState.copy(
            status = RecipeStatus.GENERATING
        )

        if(requirements == null){
            // Start to regenerate recipe and assign it to recipeState
            generateRecipe()
        }else{
            // Start to generate recipe and assign it to recipeState
            generateRecipe(
                requirements = requirements,
                preferences = preferences
            )
        }?.let {
            recipeState.recipe = it
            repository.insertRecipe(it) // Insert to book for testing only
        }

        // Change recipeState status to READY
        recipeState = recipeState.copy(
            status = RecipeStatus.READY
        )
    }
}