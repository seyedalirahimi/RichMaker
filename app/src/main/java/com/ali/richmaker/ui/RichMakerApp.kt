package com.ali.richmaker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ali.richmaker.ui.navigation.RichMakerNavGraph
import com.ali.richmaker.ui.navigation.TopLevelDestination
import com.ali.richmaker.ui.theme.RichMakerTheme

@Preview
@Composable
fun RichMakerApp(modifier: Modifier = Modifier) {
    RichMakerTheme {
        val navController = rememberNavController()
        val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries


        Scaffold(bottomBar = { RichMakerBottomBar(navController, topLevelDestinations) }) { padding ->
            RichMakerNavGraph(navController, modifier.padding(padding))
        }

    }
}