package com.ali.richmaker.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ali.richmaker.R

enum class TopLevelDestination(
    @DrawableRes val selectedDrawableId: Int,
    @DrawableRes val unselectedDrawableId: Int,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: Route
) {
    Home(
        selectedDrawableId = R.drawable.ic_home,
        unselectedDrawableId =  R.drawable.ic_home,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = Route.HomeRoute
    ),
    Analysis(
        selectedDrawableId = R.drawable.ic_analysis,
        unselectedDrawableId = R.drawable.ic_analysis,
        iconTextId = R.string.analysis,
        titleTextId = R.string.analysis,
        route = Route.AnalysisRoute
    ),
    Transaction(
        selectedDrawableId = R.drawable.ic_transaction,
        unselectedDrawableId = R.drawable.ic_transaction,
        iconTextId = R.string.transaction,
        titleTextId = R.string.transaction,
        route = Route.TransactionRoute
    ),
    Categories(
        selectedDrawableId = R.drawable.ic_category,
        unselectedDrawableId = R.drawable.ic_category,
        iconTextId = R.string.categories,
        titleTextId = R.string.categories,
        route = Route.CategoriesRoute
    ),
    Profile(
        selectedDrawableId = R.drawable.ic_profile,
        unselectedDrawableId = R.drawable.ic_profile,
        iconTextId = R.string.profile,
        titleTextId = R.string.profile,
        route = Route.ProfileRoute
    ),


}