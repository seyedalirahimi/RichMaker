package com.ali.richmaker.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ali.richmaker.ui.feature_categories.CategoriesRoute
import com.ali.richmaker.ui.feature_categoryInfo.CategoryInfoRoute
import com.ali.richmaker.ui.feature_transactions.TransactionsRoute
import kotlinx.serialization.Serializable


@Serializable
sealed class Route {
    @Serializable
    data object HomeRoute : Route()

    @Serializable
    data object AnalysisRoute : Route()

    @Serializable
    data object TransactionRoute : Route()

    @Serializable
    data object CategoriesRoute : Route()

    @Serializable
    data object ProfileRoute : Route()

    @Serializable
    data class CategoryInfoRoute(val categoryId: Int) : Route()
}

@Composable
fun RichMakerNavGraph(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, startDestination = Route.HomeRoute, modifier = modifier
    ) {
        composable<Route.HomeRoute> {
            Text("Home")
        }

        composable<Route.AnalysisRoute> {
            Text(
                text = "Analysis"
            )
        }
        composable<Route.TransactionRoute> {
            TransactionsRoute()
        }
        composable<Route.CategoriesRoute> {
            CategoriesRoute(onCategoryClick = { categoryId ->
                navController.navigate(route = Route.CategoryInfoRoute(categoryId))
            })
        }
        composable<Route.CategoryInfoRoute> { categoryInfoRoute ->
            CategoryInfoRoute()
        }
        composable<Route.ProfileRoute> {
            Text(
                text = "Profile"
            )
        }


    }
}