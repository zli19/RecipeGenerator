package com.quentin.recipegenerator.presentation.view.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.quentin.recipegenerator.R
import com.quentin.recipegenerator.presentation.ui.theme.ButtonOrHighlight
import com.quentin.recipegenerator.presentation.ui.theme.Headline
import com.quentin.recipegenerator.presentation.ui.theme.Secondary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(
    mainViewModel: MainViewModel
){
    var openDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Secondary,
            titleContentColor = Headline,
        ),
        title = {
            Text(
                text = "RecipeGPT",
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold,
                color = Headline
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.noun_cooking_3212286),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(40.dp)
                    .padding(start = 5.dp, top = 10.dp, end = 5.dp),
                tint = Headline
            )
        },
        actions = {
            if(mainViewModel.user.isEmpty()){
                Button(
                    shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                    onClick = {
                        openDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonOrHighlight)
                ) {
                    Text(text = "Login ", fontSize = TextUnit(16f, TextUnitType.Sp), color = Stroke )
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Account",
                        tint = Stroke
                    )
                }
            }else{
                IconButton(onClick = {
                    mainViewModel.user = ""
                }) {
                    Row {
                        Text(text = mainViewModel.user.first().toString().uppercase(), fontSize = TextUnit(18f, TextUnitType.Sp), color = Headline )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Account",
                            modifier = Modifier
                                .width(50.dp),
                            tint = Headline
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
    if (openDialog) {
        LoginDialog(
            onDismissRequest = {
                openDialog = false
            },
            mainViewModel
        )
    }
}