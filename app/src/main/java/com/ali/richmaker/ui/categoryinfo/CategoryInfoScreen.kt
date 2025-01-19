package com.ali.richmaker.ui.categoryinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.common.designsystem.component.RichMakerTopAppBar
import com.ali.richmaker.common.designsystem.component.TransactionItem
import com.ali.richmaker.ui.categories.CategoryHeader
import com.ali.richmaker.ui.transactions.TransactionList


@Composable
fun CategoryInfoRoute(
    categoryInfoViewModel: CategoryInfoViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = categoryInfoViewModel.state.collectAsStateWithLifecycle()
    CategoryInfoScreen(
        uiState = uiState.value,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun CategoryInfoScreen(
    uiState: CategoryInfoUiState,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary
            ),
    ) {
        RichMakerTopAppBar(
            title = uiState.categoryName,
            onBackClick = onBackClick,
        )
        CategoryHeader(
            totalBalance = uiState.totalBalance,
            totalExpense = uiState.totalExpense,
            goal = uiState.goal,
            modifier = modifier.padding(horizontal = 48.dp)
        )

        TransactionList(
            uiState.transactions,
            modifier = Modifier
        )
    }

}