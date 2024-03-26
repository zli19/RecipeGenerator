package com.quentin.recipegenerator.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.quentin.recipegenerator.presentation.ui.theme.Primary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookScreen(navController: NavController, mainViewModel: MainViewModel){
//    val recipeBook = mainViewModel.recipeBook.collectAsState()

    FlowRow(
        horizontalArrangement = Arrangement.SpaceAround
    ){
//        recipeBook.value.forEach{
        mainViewModel.recipeBook.forEach{
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Primary,
                    contentColor = Stroke
                ),
                border = BorderStroke(2.dp, Stroke),
                modifier = Modifier
                    .size(width = 200.dp, height = 250.dp)
                    .padding(10.dp)
                    .clickable {
                        mainViewModel.pushRecipeToDisplay(it, navController)
                    }
            ){
                AsyncImage(
                    model = it.images[0],
                    contentDescription = it.name,
                    modifier= Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                )
                Text(
                    text = it.name,
                    Modifier.fillMaxWidth().padding(10.dp),
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = it.time,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }


}