package com.ali.richmaker.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ali.richmaker.ui.navigation.Screen

@Composable
fun RichMakerBottomBar(
    navController: NavController,
    items: List<Screen>
) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
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
}
