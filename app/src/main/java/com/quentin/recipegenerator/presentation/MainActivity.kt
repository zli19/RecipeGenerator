package com.quentin.recipegenerator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.quentin.recipegenerator.presentation.view.navigation.RecipeScaffold
import com.quentin.recipegenerator.presentation.ui.theme.RecipeGeneratorTheme
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeGeneratorTheme {
                val navController = rememberNavController()
                RecipeScaffold(navController, mainViewModel)
            }
        }
    }
}

