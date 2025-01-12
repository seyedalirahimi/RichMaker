package com.ali.richmaker.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.data.local.database.model.CategoryEntity

@Composable
fun CategoriesRoute(
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categoriesUiState = categoriesViewModel.state.collectAsStateWithLifecycle()

    CategoriesScreen(
        uiState = categoriesUiState.value,
        onCategoryClick = onCategoryClick,
        modifier = modifier,
    )
}

@Composable
fun CategoriesScreen(
    uiState: CategoriesUiState = CategoriesUiState(),
    onCategoryClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // Display the list of categories
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState.categories.size) { index ->
            val category = uiState.categories[index]
            CategoryItem(
                categoryEntity = category,
                onClick = onCategoryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Category ${category.name}" }
            )
        }
    }
}

@Composable
fun CategoryItem(
    categoryEntity: CategoryEntity,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = { onClick(categoryEntity.id) },
        modifier = modifier
    ) {
        Text(text = categoryEntity.name)
    }
}