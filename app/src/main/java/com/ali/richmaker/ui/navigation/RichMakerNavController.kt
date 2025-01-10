package com.ali.richmaker.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
    data object CategoryBaseRoute : Route()

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
        addHomeRoute()
        addAnalysisRoute()
        addTransactionRoute()
        addCategoriesSection(navController)
        addProfileRoute()
    }
}

private fun NavGraphBuilder.addHomeRoute() {
    composable<Route.HomeRoute> {
        Text("Home")
    }
}

private fun NavGraphBuilder.addAnalysisRoute() {
    composable<Route.AnalysisRoute> {
        Text("Analysis")
    }
}

private fun NavGraphBuilder.addTransactionRoute() {
    composable<Route.TransactionRoute> {
        TransactionsRoute()
    }
}

private fun NavGraphBuilder.addCategoriesSection(navController: NavHostController) {
    navigation<Route.CategoryBaseRoute>(
        startDestination = Route.CategoriesRoute
    ) {
        composable<Route.CategoriesRoute> {
            CategoriesRoute(onCategoryClick = { categoryId ->
                navController.navigate(route = Route.CategoryInfoRoute(categoryId))
            })
        }
        composable<Route.CategoryInfoRoute> {
            CategoryInfoRoute()
        }
    }
}

private fun NavGraphBuilder.addProfileRoute() {
    composable<Route.ProfileRoute> {
        Text("Profile")
    }
}
