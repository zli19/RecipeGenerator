package com.quentin.recipegenerator.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.presentation.view.component.RecipeCard
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookScreen(navController: NavController, mainViewModel: MainViewModel){

    val recipes = mainViewModel.recipeBook
//    FlowRow(
//        horizontalArrangement = Arrangement.SpaceAround,
//        modifier = Modifier
//            .fillMaxWidth()
//            .verticalScroll(rememberScrollState())
//    ) {
//        // Solution1
//        mainViewModel.recipeBook.forEach{
//            RecipeCard(it, mainViewModel = mainViewModel, navController = navController)
//        }

        // Solution2
//        RecipeColumn(
//            recipes = mainViewModel
//                .recipeBook
//                .withIndex()
//                .filter {
//                    it.index % 2 == 0
//                }.map {
//                    it.value
//                },
//            mainViewModel = mainViewModel,
//            navController = navController
//        )
//        RecipeColumn(
//            recipes = mainViewModel
//                .recipeBook
//                .withIndex()
//                .filter {
//                    it.index % 2 != 0
//                }.map {
//                    it.value
//                },
//            mainViewModel = mainViewModel,
//            navController = navController
//        )
//    }

    // Best solution
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(
                count = recipes.size
            ){
                RecipeCard(recipe = recipes[it], mainViewModel = mainViewModel, navController = navController)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun RecipeColumn(recipes: List<Recipe>, mainViewModel: MainViewModel, navController: NavController){
    Column {
        recipes.forEach {
           RecipeCard(it, mainViewModel, navController)
        }
    }
}