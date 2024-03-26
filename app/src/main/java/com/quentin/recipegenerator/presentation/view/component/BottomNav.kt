package com.quentin.recipegenerator.presentation.view.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import com.quentin.recipegenerator.presentation.view.navigation.Destination
import com.quentin.recipegenerator.presentation.ui.theme.ButtonOrHighlight
import com.quentin.recipegenerator.presentation.ui.theme.Headline
import com.quentin.recipegenerator.presentation.ui.theme.Secondary
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel


@Composable
fun BottomNav(navController: NavController, mainViewModel: MainViewModel){

    val items = mutableListOf(
        Destination.AI,
        Destination.Book,
        Destination.Recipe
    )

    NavigationBar(containerColor = Secondary){
        items.forEach { item ->
            AddItem(
                destination = item,
                navController,
                mainViewModel
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    destination: Destination,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    NavigationBarItem(
        label = {
            Text(
                text = destination.route,
            )
        },

        icon = {
            Icon(
                painterResource(id = destination.icon),
                contentDescription = destination.route,
            )
        },

        selected = mainViewModel.currentScreen.route == destination.route,

        onClick = {
            if( mainViewModel.currentScreen == destination) return@NavigationBarItem
            navController.navigate(destination.route)
            mainViewModel.currentScreen = destination
        },

        colors = NavigationBarItemDefaults.colors(
            selectedTextColor = ButtonOrHighlight,
            selectedIconColor = ButtonOrHighlight,
            indicatorColor = Secondary,
            unselectedTextColor = Headline,
            unselectedIconColor = Headline
        )
    )
}