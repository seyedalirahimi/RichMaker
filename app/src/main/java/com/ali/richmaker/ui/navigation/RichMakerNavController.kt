package com.ali.richmaker.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ali.richmaker.ui.transactions.TransactionsViewModel
import com.ali.richmaker.ui.transactions.TransactionsScreenRoot

sealed class Screen(val route: String, val title: String) {
    data object Transaction : Screen("Transaction", "Transaction")
    data object Home : Screen("home", "Home")
    data object Search : Screen("search", "Search")
    data object Profile : Screen("profile", "Profile")
}

@Composable
fun RichMakerNavGraph(
    navController: NavHostController, modifier: Modifier
) {
    NavHost(
        navController = navController, startDestination = Screen.Home.route, modifier = modifier
    ) {

        composable(Screen.Transaction.route) {
            val tranViewModel: TransactionsViewModel = hiltViewModel()
            TransactionsScreenRoot(tranViewModel)
        }
        composable(Screen.Home.route) {
            Text(text = "Home")
        }
        composable(Screen.Search.route) {
            Text(text = "Search")
        }
        composable(Screen.Profile.route) {
            Text(text = "Profile")
        }
    }
}