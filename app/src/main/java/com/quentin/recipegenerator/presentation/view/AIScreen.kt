package com.quentin.recipegenerator.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.quentin.recipegenerator.R
import com.quentin.recipegenerator.presentation.viewmodel.RecipeStatus
import com.quentin.recipegenerator.presentation.ui.theme.Background
import com.quentin.recipegenerator.presentation.ui.theme.ButtonOrHighlight
import com.quentin.recipegenerator.presentation.ui.theme.Primary
import com.quentin.recipegenerator.presentation.ui.theme.Secondary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AIScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
){

    // Local variable to store requirements TextField value
    var requirements by remember {
        mutableStateOf(mainViewModel.recipeState.requirements)
    }

    // Local variable to store preference TextField value
    var preference by remember {
        mutableStateOf("")
    }

    // Local variable to store values of selected preference tags
    val preferences = remember{
        mutableStateListOf<String>()
    }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            color = Stroke,
            text = "Specify your requirements:",
            fontSize =  TextUnit(20f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )

        OutlinedTextField(
            value = requirements,
            textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp), color = Secondary),
            onValueChange = { requirements = it },
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(all = 10.dp)
                .border(
                    border = BorderStroke(2.dp, Secondary),
                    shape = RoundedCornerShape(CornerSize(5.dp))
                )
                .background(Background, RoundedCornerShape(5.dp)),
            placeholder = {
                Text(
                    text = "Such as ingredients, meal type, cooking method, occasion, etc.",
                    color = Primary
                )
            }
        )

        Button(
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            onClick = {
                if(requirements.trim().isEmpty()) return@Button
                mainViewModel.handleGenerateButtonClickEvent(requirements.trim(), preferences, navController)
            },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonOrHighlight),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
            enabled = mainViewModel.recipeState.status != RecipeStatus.GENERATING
        ) {
            if(mainViewModel.recipeState.status != RecipeStatus.GENERATING){
                Text(text = "Get Recipe", fontSize = TextUnit(16f, TextUnitType.Sp), color = Stroke )
            }else{
                Text(text = "Generating...", fontSize = TextUnit(16f, TextUnitType.Sp), color = Stroke )
            }
        }

        Text(
            color = Stroke,
            text = "Add a preference tag",
            fontSize =  TextUnit(18f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
        )
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = preference,
                textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp), color = Secondary),
                onValueChange = { preference = it },
                modifier = Modifier
                    .weight(1f),
                singleLine = true
            )
            IconButton(
                onClick = {
                    if(preference.trim().isEmpty()) return@IconButton
                    preferences.add(preference.trim())
                    mainViewModel.featureMap.putIfAbsent(preference, 1)
                    preference = ""
                },
                modifier = Modifier
                    .width(50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_add_40),
                    contentDescription = "customize",
                    modifier = Modifier
                        .fillMaxSize()
                )

            }

        }
        Divider(
            modifier = Modifier.padding(start = 10.dp, end = 50.dp),
            thickness = 2.dp,
            color = Primary
        )

        FlowRow {
            mainViewModel.featureMap.forEach{
                FilterChip(
                    modifier = Modifier
                        .padding(5.dp),
                    selected = preferences.contains(it.key),
                    onClick = {
                        if(preferences.contains(it.key)){
                            preferences.remove(it.key)
                        }else{
                            preferences.add(it.key)
                        }
                    },
                    label = {
                        Text(
                            text = it.key,
                            fontSize = TextUnit(15f, TextUnitType.Sp)
                        )
                    },
                    trailingIcon ={
                        IconButton(
                            onClick = {
                                mainViewModel.featureMap.remove(it.key)
                                preferences.remove(it.key)
                            },
                            modifier = Modifier
                                .width(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Delete tag",
                                tint = Stroke
                            )
                        }
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Background,
                        labelColor = Stroke
                    )
                )
            }
        }
    }
}