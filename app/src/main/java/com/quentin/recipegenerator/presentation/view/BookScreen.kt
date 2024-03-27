package com.quentin.recipegenerator.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.presentation.ui.theme.Primary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookScreen(navController: NavController, mainViewModel: MainViewModel){

    FlowRow(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        RecipeColumn(
            recipes = mainViewModel
                .recipeBook
                .withIndex()
                .filter {
                    it.index % 2 == 0
                }.map {
                    it.value
                },
            mainViewModel = mainViewModel,
            navController = navController
        )
        RecipeColumn(
            recipes = mainViewModel
                .recipeBook
                .withIndex()
                .filter {
                    it.index % 2 != 0
                }.map {
                    it.value
                },
            mainViewModel = mainViewModel,
            navController = navController
        )

    }
}

@Composable
fun RecipeColumn(recipes: List<Recipe>, mainViewModel: MainViewModel, navController: NavController){
    Column {
        recipes.forEach {
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Primary,
                    contentColor = Stroke
                ),
                border = BorderStroke(2.dp, Stroke),
                modifier = Modifier
                    .width(200.dp)
                    //                    .size(width = 200.dp, height = 250.dp)
                    .padding(10.dp)
                    .clickable {
                        mainViewModel.getRecipeFromBook(it, navController)
                    }
            ) {
                AsyncImage(
                    model = it.pictures?.first()?.original,
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                )
                Text(
                    text = it.name,
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = it.time,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
            }
        }
    }
}