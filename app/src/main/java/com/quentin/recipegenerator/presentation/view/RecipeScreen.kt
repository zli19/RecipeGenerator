package com.quentin.recipegenerator.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.quentin.recipegenerator.R
import com.quentin.recipegenerator.presentation.ui.theme.Primary
import com.quentin.recipegenerator.presentation.ui.theme.Secondary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel

@Composable
fun RecipeScreen(mainViewModel: MainViewModel){
//    val recipeState = mainViewModel.recipeState.collectAsState()
//    val recipe = recipeState.value.recipe
    val recipe = mainViewModel.recipeState.recipe
    if(recipe != null){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = recipe.pictures?.first()?.landscape,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row {
                Text(
                    text = recipe.name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    color = Stroke,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(35f, TextUnitType.Sp),
                    textAlign = TextAlign.Center,
                    lineHeight = TextUnit(40f, TextUnitType.Sp)
                )
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(painter = painterResource(id = R.drawable.icons8_like_regular_50), contentDescription = "regenerate")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(painter = painterResource(id = R.drawable.icons8_reset_50), contentDescription = "regenerate")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }


            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Primary,
                    contentColor = Secondary
                ),
                border = BorderStroke(2.dp, Stroke),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ){
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "Ingredients",
                        color = Stroke,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                    )
                    recipe.ingredients.forEach{
                        Text(
                            text = it.description,
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = TextUnit(18f, TextUnitType.Sp),
                        )
                    }
                }
            }

            recipe.instructions.withIndex().forEach{
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "${it.index+1}. ${it.value.title}",
                        color = Stroke,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Start,
                        fontSize = TextUnit(20f, TextUnitType.Sp),
                    )
                    Text(
                        text = it.value.description,
                        color = Secondary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = TextUnit(20f, TextUnitType.Sp),
                    )
                }
            }
        }
    }else{
        Text(text = "No recipe is on display.")
    }
}