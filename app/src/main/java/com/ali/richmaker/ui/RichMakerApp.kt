package com.ali.richmaker.ui

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ali.richmaker.ui.navigation.RichMakerNavGraph
import com.ali.richmaker.ui.navigation.Screen
import com.ali.richmaker.ui.theme.RichMakerTheme

@Preview
@Composable
fun RichMakerApp(modifier: Modifier = Modifier) {
    RichMakerTheme {
        val navController = rememberNavController()
        val bottomBarItems = listOf(
            Screen.Transaction,
            Screen.Categories,
            Screen.Home,
            Screen.Search,
            Screen.Profile
        )

        Scaffold(bottomBar = { RichMakerBottomBar(navController, bottomBarItems) }) { padding ->
            RichMakerNavGraph(navController, modifier.padding(padding))
        }

    }
}