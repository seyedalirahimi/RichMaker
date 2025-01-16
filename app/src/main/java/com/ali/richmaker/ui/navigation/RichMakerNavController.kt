package com.ali.richmaker.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ali.richmaker.ui.categories.CategoriesRoute
import com.ali.richmaker.ui.categoryinfo.CategoryInfoRoute
import com.ali.richmaker.ui.transactions.TransactionsRoute
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
        navController = navController, startDestination = Route.TransactionRoute, modifier = modifier
    ) {
        addHomeRoute(navController)
        addAnalysisRoute(navController)
        addTransactionRoute(navController)
        addCategoriesSection(navController)
        addProfileRoute(navController)
    }
}

private fun NavGraphBuilder.addHomeRoute(navController: NavHostController) {
    composable<Route.HomeRoute> {
        Text("Home")
    }
}

private fun NavGraphBuilder.addAnalysisRoute(navController: NavHostController) {
    composable<Route.AnalysisRoute> {
        Text("Analysis")
    }
}

private fun NavGraphBuilder.addTransactionRoute(navController: NavHostController) {
    composable<Route.TransactionRoute> {
        TransactionsRoute(onBackClick = { navController.popBackStack() })
    }
}

private fun NavGraphBuilder.addCategoriesSection(navController: NavHostController) {
    navigation<Route.CategoryBaseRoute>(
        startDestination = Route.CategoriesRoute
    ) {
        composable<Route.CategoriesRoute> {
            CategoriesRoute(
                onCategoryClick = { categoryId ->
                    navController.navigate(route = Route.CategoryInfoRoute(categoryId))
                }, onBackClick = { navController.popBackStack() })
        }
        composable<Route.CategoryInfoRoute> {
            CategoryInfoRoute(onBackClick = { navController.popBackStack() })
        }
    }
}

private fun NavGraphBuilder.addProfileRoute(navController: NavHostController) {
    composable<Route.ProfileRoute> {
        Text("Profile")
    }
}
