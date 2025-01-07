package com.ali.richmaker.ui.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.data.local.database.TransactionWithCategory

@Composable
fun TransactionsScreenRoot(
    transactionsViewModel: TransactionsViewModel, modifier: Modifier = Modifier
) {
    val transactionsUiState = transactionsViewModel.state.collectAsStateWithLifecycle()
    TransactionsScreen(
        uiState = transactionsUiState.value,
        modifier = modifier,
    )
}


@Composable
fun TransactionsScreen(
    uiState: TransactionsUiState = TransactionsUiState(),
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        Text(text = "Loading...")
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Balance: ${uiState.totalBalance.let { "$$it" }}"
            )

            Text(
                text = "Total Income: ${uiState.totalIncome.let { "$$it" }}",

                )

            Text(
                text = "Total Expense: ${uiState.totalExpense.let { "$$it" }}",

                )
            Text(text = "Transactions:")
            if (uiState.transactions.isEmpty()) {
                Text(text = "No transactions available.")
            } else {
                LazyColumn {
                    uiState.transactions.forEach { transactionInMonth ->
                        item {
                            Text(text = transactionInMonth.month)
                        }

                        transactionInMonth.transactions.forEach { transaction ->
                            item {
                                TransactionItem(transaction)
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun TransactionItem(transaction: TransactionWithCategory) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Category: ${transaction.categoryName}")
        Text(text = "Amount: $${transaction.amount}")
        Text(text = "Date: ${transaction.date}")
    }
}


