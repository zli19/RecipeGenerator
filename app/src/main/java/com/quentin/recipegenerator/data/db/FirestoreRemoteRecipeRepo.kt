package com.quentin.recipegenerator.data.db

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.model.User
import com.quentin.recipegenerator.domain.repository.RemoteRecipeRepository
import kotlinx.coroutines.tasks.await

class FirestoreRemoteRecipeRepo: RemoteRecipeRepository {

    val fs = Firebase.firestore

    // Get all recipes for a specified user from Firestore db
    override suspend fun getAllRecipes(user: User): List<Recipe> {
        var returnValue : List<Recipe> =  emptyList()

        try {
            fs.collection("recipeGPT")
                .document(user.email!!)
                .collection("recipes")
                .get()
                .addOnSuccessListener {
                    Log.i("FireStore", "Success getting all recipe documents!")
                }
                .addOnFailureListener{exception->
                    Log.w("FireStore", "Error getting documents: ", exception)
                }.await().documents.apply {
                    returnValue = this.map {
                        it.toObject<Recipe>()!!
                    }
                }
        }catch (e: Exception){
            Log.w("FireStore", "Error getting documents: ", e)
        }

        return returnValue
    }

    // Insert a recipe for a specified user into Firestore db
    override suspend fun insertRecipe(recipe: Recipe, user: User) {

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

    // Delete a recipe for a specified user from Firestore db
    override suspend fun deleteRecipe(recipe: Recipe, user: User) {
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
}