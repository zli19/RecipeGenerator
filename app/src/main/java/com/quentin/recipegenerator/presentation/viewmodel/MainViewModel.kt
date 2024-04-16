package com.quentin.recipegenerator.presentation.viewmodel

import android.util.Log
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.repository.RecipeRepository
import com.quentin.recipegenerator.domain.usecase.GenerateRecipe
import com.quentin.recipegenerator.presentation.view.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.jvm.Throws

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val generateRecipe: GenerateRecipe
): ViewModel(){
    // Local variable to store current user
    var user by mutableStateOf<FirebaseUser?>(null)
    // Local variable to store current destination
    var currentScreen by mutableStateOf<Destination>(Destination.AI)

    // Local variable to store recipe state
    var recipeState by mutableStateOf(RecipeState())

    // Local variable to store all recipes from local room db.
    var recipeBook by mutableStateOf<List<Recipe>>(emptyList())
    // Local variable to store all preference tags for display
    var preferenceMap = mutableStateMapOf<String, Int>()
    // Firestore instance
    private val fs = Firebase.firestore


    init {
        // Retrieve data from database
        viewModelScope.launch {
            repository.getAllRecipes().collectLatest { recipes ->
                recipeBook = recipes
                val map = HashMap<String, Int>()
                for (preference in recipeBook.flatMap { it.preferences }) {
                    map[preference] = map.getOrDefault(preference, 0) + 1
                }
                preferenceMap.putAll(map.toList().sortedBy { -it.second }.toMap())
            }
        }
    }

    // Handles the click event for the generate and regenerate button.
    fun onGenerateButtonClicked(
        requirements: String = "",
        preferences: List<String> = emptyList(),
        navController: NavController
    ) = viewModelScope.launch {

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
                )
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
            val id = repository.insertRecipe(this)
            repository.getRecipeById(id).apply {
                recipeState = recipeState.copy(
                    recipe = this
                )
                addRecipeToFirestore(user, this)
            }
        }
    }

    // Delete recipe from book
    fun onUnlikeButtonClicked()= viewModelScope.launch{
        recipeState.recipe.apply {
            repository.deleteRecipe(this)
            val recipe = this.copy(id = 0L)
            recipeState = recipeState.copy(
                recipe = recipe
            )
            deleteRecipeFromFirestore(user, this)
        }
    }

    // Asynchronously launch the AI service to start generating recipe and update the value to recipeState
    private fun getRecipe(
        requirements: String,
        preferences: List<String>,
        exclusions: List<String> = emptyList()
    ) = viewModelScope.launch(Dispatchers.IO) {

        // Change recipeState status to GENERATING
        recipeState = recipeState.copy(
            status = RecipeStatus.GENERATING
        )

        // Start to generate recipe and assign it to recipeState
        generateRecipe(
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

    // Retrieve recipe data from Firestore and upsert into local room db
    fun syncDataFromFirestore(user: FirebaseUser?) = viewModelScope.launch(Dispatchers.IO){
        if(user == null) return@launch

        fs.collection("recipeGPT")
            .document(user.email!!)
            .collection("recipes")
            .get()
            .addOnSuccessListener {documents->
                documents.map { document->
                    document.toObject<Recipe>()
                }.apply {
                    viewModelScope.launch(Dispatchers.IO) {
                        if(hasUserChanged(user)){
                            recipeState = RecipeState()
                            preferenceMap.clear()
                            async {
                                repository.clear()
                            }.await()
                        }
                        repository.upsertAllRecipes(this@apply)
                    }
                }
            }
            .addOnFailureListener{exception->
                Log.w("FireStore", "Error getting documents: ", exception)
            }
    }

    // Add a recipe to Firestore
    private fun addRecipeToFirestore(user: FirebaseUser?, recipe: Recipe) = viewModelScope.launch(Dispatchers.IO){
        if(user == null) return@launch

        fs.collection("recipeGPT")
            .document(user.email!!)
            .collection("recipes")
            .document(recipe.id.toString())
            .set(recipe, SetOptions.merge())
            .addOnSuccessListener {
                Log.i("FireStore", "Success setting recipe ${recipe.name}")
            }
            .addOnFailureListener{ e ->
                Log.w("FireStore", "Error setting document", e)
            }
    }

    // Delete a recipe from Firestore
    private fun deleteRecipeFromFirestore(user: FirebaseUser?, recipe: Recipe) = viewModelScope.launch(Dispatchers.IO){
        if(user == null) return@launch

        fs.collection("recipeGPT")
            .document(user.email!!)
            .collection("recipes")
            .document(recipe.id.toString())
            .delete()
            .addOnSuccessListener {
                Log.i("FireStore", "Success deleting recipe ${recipe.name}")
            }
            .addOnFailureListener{ e ->
                Log.w("FireStore", "Error deleting document", e)
            }
    }

    // Check if logged in user is the owner of the recipe book.
    private fun hasUserChanged(user: FirebaseUser): Boolean{
        if(recipeBook.isEmpty()) return false
        return user.uid != recipeBook[0].uid
    }
}