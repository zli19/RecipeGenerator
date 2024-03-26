package com.quentin.recipegenerator.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.quentin.recipegenerator.data.api.openaiapi.RecipeAI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.model.RecipeState
import com.quentin.recipegenerator.domain.model.RecipeStatus
import com.quentin.recipegenerator.domain.repository.RecipeRepository
import com.quentin.recipegenerator.presentation.view.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val recipeAI: RecipeAI
): ViewModel(){
    
    var user by mutableStateOf("")
    var currentScreen by mutableStateOf<Destination>(Destination.AI)

//    private var _recipeState = MutableStateFlow(RecipeState())
//    val recipeState: StateFlow<RecipeState> = _recipeState
    var recipeState by mutableStateOf(RecipeState())
//
//    private val _recipeBook = MutableStateFlow<List<Recipe>>(emptyList())
//    val recipeBook: StateFlow<List<Recipe>> = _recipeBook
    var recipeBook by mutableStateOf<List<Recipe>>(emptyList())

    init {
        // Retrieve data from database
        viewModelScope.launch {
            repository.getAllRecipes().collectLatest {
//                _recipeBook.value = it
                recipeBook = it
            }
        }
    }


    // Handles the click event for the generate button
    fun onGenerateButtonClicked(
        input: String, navController: NavController
    ) = viewModelScope.launch {
//            _recipeState.value.input =  input
            recipeState.input = input
            addIngredients(ingredients = input)
            getRecipe().join() // Wait until recipe is ready then navigate automatically
            navController.navigate(Destination.Recipe.route)
            currentScreen = Destination.Recipe
    }

    fun addToRecipeBook()= viewModelScope.launch(Dispatchers.IO) {
//        repository.insertRecipe(_recipeState.value.recipe!!)
        repository.insertRecipe(recipeState.recipe!!)
    }


    fun pushRecipeToDisplay(recipe: Recipe, navController: NavController){
//        _recipeState.value = _recipeState.value.copy(
        recipeState = recipeState.copy(
            recipe = recipe,
            status = RecipeStatus.READY
        )
        navController.navigate(Destination.Recipe.route)
        currentScreen = Destination.Recipe
    }


    // Add user message of the ingredients to OpenAI chatMessages
    private fun addIngredients(ingredients: String){
        recipeAI.addUserMessage(ingredients)
    }

    // asynchronously launch the OPENAI API to start generating recipe and update the value to _recipeState flow
    private fun getRecipe() = viewModelScope.launch(Dispatchers.IO) {
//        _recipeState.value = _recipeState.value.copy(
        recipeState = recipeState.copy(
            status = RecipeStatus.GENERATING
        )

        recipeAI.generate()?.let {
//            _recipeState.value.recipe = it
            recipeState.recipe = it
            repository.insertRecipe(it)
        }

//        _recipeState.value = _recipeState.value.copy(
        recipeState = recipeState.copy(
            status = RecipeStatus.READY
        )
    }
}