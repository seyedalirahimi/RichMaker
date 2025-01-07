package com.ali.richmaker.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CategoriesScreenRoot(
    categoriesViewModel: CategoriesViewModel,
    modifier: Modifier = Modifier,
    ) {
    val categoriesUiState = categoriesViewModel.state.collectAsStateWithLifecycle()

    CategoriesScreen(
        uiState = categoriesUiState.value,
        modifier = modifier,
    )
}

@Composable
fun CategoriesScreen(
    uiState: CategoriesUiState = CategoriesUiState(),
    modifier: Modifier = Modifier,
) {
    Column {
        uiState.categories.forEach { category ->
            Text(text = category.name)
        }
    }
}