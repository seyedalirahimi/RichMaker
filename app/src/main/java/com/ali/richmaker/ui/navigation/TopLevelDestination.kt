package com.ali.richmaker.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.ali.richmaker.R
import com.ali.richmaker.ui.icon.RichMakerIcon
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: Route
) {
    Home(
        selectedIcon = RichMakerIcon.home,
        unselectedIcon = RichMakerIcon.homeBorder,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = Route.HomeRoute
    ),
    Analysis(
        selectedIcon = RichMakerIcon.home,
        unselectedIcon = RichMakerIcon.homeBorder,
        iconTextId = R.string.analysis,
        titleTextId = R.string.analysis,
        route = Route.AnalysisRoute
    ),
    Transaction(
        selectedIcon = RichMakerIcon.home,
        unselectedIcon = RichMakerIcon.homeBorder,
        iconTextId = R.string.transaction,
        titleTextId = R.string.transaction,
        route = Route.TransactionRoute
    ),
    Categories(
        selectedIcon = RichMakerIcon.home,
        unselectedIcon = RichMakerIcon.homeBorder,
        iconTextId = R.string.categories,
        titleTextId = R.string.categories,
        route = Route.CategoriesRoute
    ),
    Profile(
        selectedIcon = RichMakerIcon.home,
        unselectedIcon = RichMakerIcon.homeBorder,
        iconTextId = R.string.profile,
        titleTextId = R.string.profile,
        route = Route.ProfileRoute
    ),


}