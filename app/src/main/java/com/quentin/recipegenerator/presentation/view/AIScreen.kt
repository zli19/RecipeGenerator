package com.quentin.recipegenerator.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.quentin.recipegenerator.domain.model.RecipeState
import com.quentin.recipegenerator.domain.model.RecipeStatus
import com.quentin.recipegenerator.presentation.ui.theme.Background
import com.quentin.recipegenerator.presentation.ui.theme.ButtonOrHighlight
import com.quentin.recipegenerator.presentation.ui.theme.ButtonText
import com.quentin.recipegenerator.presentation.ui.theme.Secondary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel

@Composable
fun AIScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
){
//    val recipeState = mainViewModel.recipeState.collectAsState()
    var input by remember {
//        mutableStateOf(recipeState.value.input)
        mutableStateOf(mainViewModel.recipeState.input)
    }

    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            color = Stroke,
            text = "What ingredients do you have on hand?",
            fontSize =  TextUnit(20f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
        )
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(
                    border = BorderStroke(2.dp, Stroke),
                    shape = RoundedCornerShape(CornerSize(5.dp))
                )
                .background(Background, RoundedCornerShape(5.dp))
        ){
            BasicTextField(
                value = input,
                textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp), color = Secondary),
                onValueChange = { input = it },
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            )
        }

        Button(
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            onClick = {
                if(input.trim().isEmpty()) return@Button
                mainViewModel.onGenerateButtonClicked(input, navController)
            },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonOrHighlight),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
//            enabled = recipeState.value.status != RecipeStatus.GENERATING
            enabled = mainViewModel.recipeState.status != RecipeStatus.GENERATING
        ) {
//            if(recipeState.value.status != RecipeStatus.GENERATING){
            if(mainViewModel.recipeState.status != RecipeStatus.GENERATING){
                Text(text = "Get Recipe", fontSize = TextUnit(16f, TextUnitType.Sp), color = ButtonText )
            }else{
                Text(text = "Generating...", fontSize = TextUnit(16f, TextUnitType.Sp), color = ButtonText )
            }
        }
    }
}