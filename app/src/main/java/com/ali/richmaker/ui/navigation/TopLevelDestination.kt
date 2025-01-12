package com.ali.richmaker.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.ali.richmaker.R
import com.ali.richmaker.common.designsystem.icon.RichMakerPainter

enum class TopLevelDestination(
    val selectedPainter: @Composable () -> Painter,
    val unselectedPainter: @Composable () -> Painter,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: Route,
    val baseRoute: Route = route,
) {
    Home(
        selectedPainter = RichMakerPainter.Home,
        unselectedPainter =  RichMakerPainter.Home,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = Route.HomeRoute
    ),
    Analysis(
        selectedPainter = RichMakerPainter.Analysis,
        unselectedPainter = RichMakerPainter.Analysis,
        iconTextId = R.string.analysis,
        titleTextId = R.string.analysis,
        route = Route.AnalysisRoute
    ),
    Transaction(
        selectedPainter = RichMakerPainter.Transaction,
        unselectedPainter = RichMakerPainter.Transaction,
        iconTextId = R.string.transaction,
        titleTextId = R.string.transaction,
        route = Route.TransactionRoute
    ),
    Categories(
        selectedPainter = RichMakerPainter.Categories,
        unselectedPainter = RichMakerPainter.Categories,
        iconTextId = R.string.categories,
        titleTextId = R.string.categories,
        route = Route.CategoriesRoute,
        baseRoute = Route.CategoryBaseRoute,
    ),
    Profile(
        selectedPainter = RichMakerPainter.Profile,
        unselectedPainter = RichMakerPainter.Profile,
        iconTextId = R.string.profile,
        titleTextId = R.string.profile,
        route = Route.ProfileRoute
    ),


}