package com.quentin.recipegenerator.presentation.viewmodel

import android.content.Context
import android.widget.Toast
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
import com.quentin.recipegenerator.domain.model.User
import com.quentin.recipegenerator.domain.usecase.UseCases
import com.quentin.recipegenerator.presentation.view.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel(){
    // Local variable to store current user
    var user by mutableStateOf<User?>(null)
    // Local variable to store current destination
    var currentScreen by mutableStateOf<Destination>(Destination.AI)

    // Local variable to store recipe state
    var recipeState by mutableStateOf(RecipeState())

    // Local variable to store all recipes from local room db.
    var recipeBook by mutableStateOf<List<Recipe>>(emptyList())
    // Local variable to store all preference tags for display
    var preferenceMap = mutableStateMapOf<String, Int>()

    init {
        // Retrieve data from database
        viewModelScope.launch(Dispatchers.IO){
            useCases.dataPersistence.getAllLocalRecipes().collectLatest { recipes ->
                recipeBook = recipes
                val map = HashMap<String, Int>()
                for (preference in recipeBook.flatMap { it.preferences }) {
                    map[preference] = map.getOrDefault(preference, 0) + 1
                }
                preferenceMap.putAll(map.toList().sortedBy { -it.second }.toMap())
            }
        }
    }

    // Sign in a user with provided credentials
    fun signIn(
        email: String,
        password: String,
        context: Context
    ) = viewModelScope.launch{
        val newUser = async {
            useCases.userAuthentication.signIn(email, password)
        }.await()
        if(newUser != null){
            if(hasUserChanged(newUser)){
                recipeState = RecipeState()
                preferenceMap.clear()
            }
            // Synchronize local data with remote data
            useCases.dataPersistence
                .syncLocalDataFromRemote(newUser, hasUserChanged(newUser))
            user = newUser

        }else{
            Toast.makeText(
                context,
                "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Sign out a user
    fun signOut()= viewModelScope.launch{
        useCases.userAuthentication.signOut()
        user = null
    }


    // Handles the click event for the generate and regenerate button.
    fun onGenerateButtonClicked(
        requirements: String = "",
        preferences: List<String> = emptyList(),
        navController: NavController
    ) = viewModelScope.launch{

        if(requirements.isNotBlank()){
            // When requirements is not blank, it is new round of recipe generation.
            // Clear history.
            recipeState.history.clear()

            getRecipe(requirements, preferences)
                .join()
        }else{
            // else it is a regeneration request. Using the same requirements and preferences.
            recipeState.recipe.apply {
                getRecipe(
                    this.requirements,
                    this.preferences,
                    recipeState.history
                ).join()
            }
        }

        // Wait until recipe is ready then navigate automatically
        navController.navigate(Destination.Recipe.route)
        currentScreen = Destination.Recipe
    }

    // Handles the click event for the recipe card
    fun onRecipeCardClicked(recipe: Recipe, navController: NavController){
        recipeState.recipe = recipe
        recipeState.status = RecipeStatus.READY

        // Clear history and add the current recipe name to history.
        recipeState.history.clear()
        recipeState.history.add(recipe.name!!)

        navController.navigate(Destination.Recipe.route)
        currentScreen = Destination.Recipe
    }

    // Insert recipe into book
    fun onLikeButtonClicked() = viewModelScope.launch {
        recipeState.recipe.apply {
            useCases.dataPersistence.saveRecipe(this, user!!).apply {
                recipeState = recipeState.copy(
                    recipe = this
                )
            }
        }
    }

    // Delete recipe from book
    fun onDislikeButtonClicked()= viewModelScope.launch{
        recipeState.recipe.apply {
            useCases.dataPersistence.deleteRecipe(this, user!!)
            val recipe = this.copy(id = 0L)
            recipeState = recipeState.copy(
                recipe = recipe
            )
        }
    }

    // Asynchronously launch the AI service to start generating recipe and update the value to recipeState
    private fun getRecipe(
        requirements: String,
        preferences: List<String>,
        exclusions: List<String> = emptyList()
    ) = viewModelScope.launch {

        // Change recipeState status to GENERATING
        recipeState = recipeState.copy(
            status = RecipeStatus.GENERATING
        )

        // Start to generate recipe and assign it to recipeState
        useCases.generateRecipe(
            requirements = requirements,
            preferences = preferences,
            exclusions = exclusions
        )?.let {
            // Update the recipe stored in recipeState to be the newly generated one.
            recipeState.recipe = it.copy(
                uid = user!!.uid
            )
            // Add its name to history for potential regeneration.
            recipeState.history.add(it.name!!)
        }

        // Change recipeState status to READY
        recipeState = recipeState.copy(
            status = RecipeStatus.READY
        )
    }

    // Check if logged in user is the owner of the recipe book.
    private fun hasUserChanged(user: User): Boolean{
        if(recipeBook.isEmpty()) return false
        return user.uid != recipeBook[0].uid
    }
}