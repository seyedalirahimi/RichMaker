package com.ali.richmaker.ui.feature_categoryInfo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.R
import com.ali.richmaker.ui.feature_transactions.TransactionItem


@Composable
fun CategoryInfoRoute(
    categoryInfoViewModel: CategoryInfoViewModel = hiltViewModel(), modifier: Modifier = Modifier
) {
    val uiState = categoryInfoViewModel.state.collectAsStateWithLifecycle()
    CategoryInfo(
        uiState = uiState.value,
        modifier = modifier
    )
}

@Composable
fun CategoryInfo(
    uiState: CategoryInfoUiState, modifier: Modifier = Modifier
) {
    if (uiState.transactions.isEmpty()) {
        Text(text = "No transactions available.")
    } else {
        LazyColumn {
            uiState.transactions.forEach { transaction ->
                item {
                    TransactionItem(
                        imageResourceId =  R.drawable.ic_transaction ,
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