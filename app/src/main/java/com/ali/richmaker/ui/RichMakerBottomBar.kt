package com.ali.richmaker.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ali.richmaker.ui.navigation.TopLevelDestination
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RichMakerBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    items: List<TopLevelDestination>
) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { destination ->

            val destinationName = destination.route::class.qualifiedName
            val selected = currentRoute == destinationName
            NavigationBarItem(icon = {
                Icon(
                    painter = painterResource(
                        if (selected) destination.selectedDrawableId else destination.unselectedDrawableId
                    ), contentDescription = stringResource(destination.iconTextId),
                    modifier = modifier.size(24.dp)
                )
            },
                label = { stringResource(destination.titleTextId) },
                selected = selected,
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
                })
        }
    }
}
