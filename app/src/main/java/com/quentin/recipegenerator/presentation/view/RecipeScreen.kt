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
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.quentin.recipegenerator.R
import com.quentin.recipegenerator.presentation.ui.theme.Primary
import com.quentin.recipegenerator.presentation.ui.theme.Secondary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.ui.theme.Tertiary
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel
import com.quentin.recipegenerator.presentation.viewmodel.RecipeStatus

@Composable
fun RecipeScreen(navController: NavController, mainViewModel: MainViewModel){

    val recipe = mainViewModel.recipeState.recipe

    if(recipe.name != null){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = recipe.picture,
                contentDescription = "Image of ${recipe.name}",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    color = Stroke,
                    lineHeight = TextUnit(32f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(30f, TextUnitType.Sp),
                    textAlign = TextAlign.Center
                )
                if(mainViewModel.user != null){
                    if (recipe.id == 0L){
                        IconButton(
                            onClick = {
                                mainViewModel.onLikeButtonClicked()
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icons8_like_regular_100),
                                contentDescription = "add to book"
                            )
                        }
                    }else{
                        IconButton(
                            onClick = {
                                mainViewModel.onDislikeButtonClicked()
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icons8_like_red_100),
                                contentDescription = "delete from book"
                            )
                        }
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(start = 10.dp, end = 50.dp),
                thickness = 2.dp,
                color = Primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.info!!,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    color = Stroke,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    textAlign = TextAlign.Start
                )
                if(mainViewModel.user != null){
                    IconButton(
                        onClick = {
                            mainViewModel.onGenerateButtonClicked(navController = navController)
                        },
                        enabled = mainViewModel.recipeState.status != RecipeStatus.GENERATING
                    ) {
                        Image(painter = painterResource(id = R.drawable.icons8_reset_100), contentDescription = "regenerate")
                    }
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
                        text = "INGREDIENTS",
                        color = Stroke,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                    )

                    Text(
                        text = recipe.ingredients!!,
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                    )
                }
            }

            Text(
                text = "DIRECTIONS",
                color = Stroke,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = TextUnit(20f, TextUnitType.Sp),
            )
            Divider(
                modifier = Modifier.padding(start = 10.dp, end = 50.dp),
                thickness = 2.dp,
                color = Primary
            )
            Text(
                text = recipe.directions!!,
                color = Secondary,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Start,
                fontSize = TextUnit(18f, TextUnitType.Sp),
            )

            Text(
                text = "NUTRITION FACTS",
                color = Stroke,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = TextUnit(20f, TextUnitType.Sp),
            )
            Divider(
                modifier = Modifier.padding(start = 10.dp, end = 50.dp),
                thickness = 2.dp,
                color = Primary
            )
            Text(
                text = recipe.nutrition!!,
                color = Secondary,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Start,
                fontSize = TextUnit(18f, TextUnitType.Sp),
            )
        }
    }else{
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "No recipe is on display...",
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(26f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )
            Text(
                text = "Generate or choose a recipe first!",
                fontWeight = FontWeight.Bold,
                color = Tertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}