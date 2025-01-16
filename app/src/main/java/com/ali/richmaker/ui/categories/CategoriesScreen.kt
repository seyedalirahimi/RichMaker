package com.ali.richmaker.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.common.designsystem.component.RichMakerIconButton
import com.ali.richmaker.common.designsystem.component.RichMakerTopAppBar
import com.ali.richmaker.common.designsystem.icon.RichMakerPainter
import com.ali.richmaker.common.designsystem.icon.getCategoryIcon

@Composable
fun CategoriesRoute(
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (Int) -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val categoriesUiState = categoriesViewModel.state.collectAsStateWithLifecycle()

    CategoriesScreen(
        uiState = categoriesUiState.value,
        onCategoryClick = onCategoryClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
fun CategoriesScreen(
    uiState: CategoriesUiState = CategoriesUiState(),
    onCategoryClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column {
        RichMakerTopAppBar(
            title = "Categories",
            onBackClick = onBackClick,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.categories.size) { index ->
                println(index)
                val category = uiState.categories[index]
                RichMakerIconButton(label = category.name,
                    icon = RichMakerPainter.getCategoryIcon(category.name)(),
                    onClick = { onCategoryClick(category.id) },
                    modifier = Modifier.semantics {
                        contentDescription = "Category ${category.name}"
                    })

            }
        }
    }
}
