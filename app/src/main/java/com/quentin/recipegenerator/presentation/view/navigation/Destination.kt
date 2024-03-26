package com.quentin.recipegenerator.presentation.view.navigation

import com.quentin.recipegenerator.R


sealed class Destination(val route: String, val icon: Int){
    data object AI: Destination("AI", R.drawable.icons8_bot_100)

    data object Book: Destination("Book", R.drawable.icons8_recipe_100)

    data object Recipe: Destination("Recipe", R.drawable.icons8_cooking_100)
}
