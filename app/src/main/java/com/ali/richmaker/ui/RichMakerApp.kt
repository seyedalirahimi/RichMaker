package com.ali.richmaker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ali.richmaker.common.designsystem.component.RichMakerNavigationBar
import com.ali.richmaker.common.designsystem.component.RichMakerNavigationBarItem
import com.ali.richmaker.common.designsystem.theme.RichMakerTheme
import com.ali.richmaker.ui.navigation.RichMakerNavGraph
import com.ali.richmaker.ui.navigation.TopLevelDestination

@Preview
@Composable
fun RichMakerApp(modifier: Modifier = Modifier) {
    RichMakerTheme {
        val navController = rememberNavController()
        val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

        Scaffold(modifier = modifier,
            bottomBar = {
                RichMakerNavigationBar(
                    modifier = Modifier
                ) {
                    val currentDestination =
                        navController.currentBackStackEntryAsState().value?.destination
                    topLevelDestinations.forEach { destination ->
                        val destinationName = destination.baseRoute::class.qualifiedName
                        val selected =
                            currentDestination?.hierarchy?.any { it.route == destinationName }
                                ?: false
                        RichMakerNavigationBarItem(
                            selected = selected,
                            icon = {
                                Icon(
                                    painter = destination.selectedPainter(),
                                    contentDescription = stringResource(destination.iconTextId),
                                    modifier = modifier.size(24.dp)
                                )
                            },
                            label = { stringResource(destination.titleTextId) },
                            alwaysShowLabel = true,
                            modifier = Modifier,
                            onClick = {
                                if (!selected) {
                                    navController.navigate(destination.route) {
                                        // Avoid multiple copies of the same destination in the back stack
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        // Restore state when re-selecting a previously selected item
                                        restoreState = true
                                        // Avoid re-selecting the same route
                                        launchSingleTop = true
                                    }
                                }
                            }

                        )
                    }
                }

            }) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary) // Set your desired background color
                        .padding(innerPadding)
                ) {
                    RichMakerNavGraph(navController, modifier)
                }
        }


    }
}