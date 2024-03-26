package com.quentin.recipegenerator.presentation.view.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quentin.recipegenerator.presentation.view.AIScreen
import com.quentin.recipegenerator.presentation.view.BookScreen
import com.quentin.recipegenerator.presentation.view.RecipeScreen
import com.quentin.recipegenerator.presentation.view.component.BottomNav
import com.quentin.recipegenerator.presentation.view.component.TopNav
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel

@Composable
fun RecipeScaffold(
    navController: NavHostController,
    mainViewModel: MainViewModel,
){
    Scaffold(
        topBar = {
            TopNav(mainViewModel)
        },
        bottomBar={
            BottomNav(navController = navController, mainViewModel)
        },

        ){innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            NavHost(navController = navController, startDestination = Destination.AI.route) {
                composable(Destination.AI.route) {
                    AIScreen(navController, mainViewModel)
                }
                composable(Destination.Book.route) {
                    BookScreen(navController, mainViewModel)
                }
                composable(Destination.Recipe.route) {
                    RecipeScreen(mainViewModel)
                }
            }
        }
    }
}