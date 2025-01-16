package com.ali.richmaker.ui.categoryinfo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.R
import com.ali.richmaker.ui.transactions.TransactionItem


@Composable
fun CategoryInfoRoute(
    categoryInfoViewModel: CategoryInfoViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = categoryInfoViewModel.state.collectAsStateWithLifecycle()
    CategoryInfo(
        uiState = uiState.value, onBackClick = onBackClick, modifier = modifier
    )
}

@Composable
fun CategoryInfo(
    uiState: CategoryInfoUiState, onBackClick: () -> Unit = {}, modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBackClick)
    if (uiState.transactions.isEmpty()) {
        Text(text = "No transactions available.")
    } else {
        Column {
            Text(
                text = uiState.categoryName
            )

            LazyColumn {
                uiState.transactions.forEach { transaction ->
                    item {
                        TransactionItem(
                            title = transaction.transactionEntity.title,
                            date = transaction.transactionEntity.date,
                            category = transaction.categoryEntity.name,
                            amount = transaction.transactionEntity.amount,
                            modifier = modifier,
                        )
                    }
                }
            }
        }
    }

}