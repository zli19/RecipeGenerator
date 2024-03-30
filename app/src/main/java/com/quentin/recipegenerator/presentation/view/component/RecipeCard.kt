package com.quentin.recipegenerator.presentation.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun RecipeCard(recipe: Recipe, mainViewModel:MainViewModel, navController: NavController){
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Primary,
            contentColor = Stroke
        ),
        border = BorderStroke(2.dp, Stroke),
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                mainViewModel.getRecipeFromBook(recipe, navController)
            }
    ) {
        AsyncImage(
            model = recipe.picture,
            contentDescription = recipe.name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
        )
        Text(
            text = recipe.name,
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            fontSize = TextUnit(18f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = recipe.time,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        )
    }
}