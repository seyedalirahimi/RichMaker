package com.ali.richmaker.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.common.designsystem.component.FinancialIconButton
import com.ali.richmaker.common.designsystem.component.RichMakerTopAppBar
import com.ali.richmaker.common.designsystem.component.TransactionItem
import com.ali.richmaker.data.local.database.model.TransactionsInMonthModel

@Composable
fun TransactionsRoute(
    viewModel: TransactionsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    TransactionsScreen(
        uiState = uiState.value,
        onBalanceClick = { viewModel.onEvent(TransactionEvent.OnBalanceClick) },
        onIncomeClick = { viewModel.onEvent(TransactionEvent.OnIncomeClick) },
        onExpenseClick = { viewModel.onEvent(TransactionEvent.OnExpenseClick) },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}


@Composable
fun TransactionsScreen(
    uiState: TransactionsUiState = TransactionsUiState(),
    onBalanceClick: () -> Unit = {},
    onIncomeClick: () -> Unit = {},
    onExpenseClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.tertiary),
    ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RichMakerTopAppBar(
                    title = "Transactions",
                    onBackClick = onBackClick,
                    )

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    FinancialIconButton(
                        label = "Total Balance",
                        isEnable = uiState.transactionsFilterMode == TransactionsFilterMode.Balance,
                        amount = uiState.totalBalance,
                        hasIcon = false,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onBalanceClick
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        FinancialIconButton(
                            label = "Income",
                            isEnable = uiState.transactionsFilterMode == TransactionsFilterMode.Income,
                            amount = uiState.totalIncome,
                            modifier = Modifier.weight(1f),
                            onClick = onIncomeClick
                        )
                        Spacer(Modifier.size(8.dp))
                        FinancialIconButton(
                            label = "Expense",
                            isEnable = uiState.transactionsFilterMode == TransactionsFilterMode.Expense,
                            amount = uiState.totalExpense,
                            modifier = Modifier.weight(1f),
                            onClick = onExpenseClick
                        )
                    }
                }
                Spacer(modifier = Modifier.size(18.dp))
            }
        TransactionList(
            uiState.transactions,
            modifier = Modifier
        )
    }
}

@Composable
fun TransactionList(
    transactions: List<TransactionsInMonthModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(topStartPercent = 15, topEndPercent = 15)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {

        transactions.forEachIndexed { index, transactionsInMonthModel ->
            item {
                if (index == 0) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                Text(
                    text = transactionsInMonthModel.month,
                    fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                )
            }

            transactionsInMonthModel.transactions.forEach { transaction ->
                item {
                    TransactionItem(
                        title = transaction.transactionEntity.title,
                        date = transaction.transactionEntity.date,
                        category = transaction.categoryEntity.name,
                        amount = transaction.transactionEntity.amount,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

        }

        items(
            count = transactions.size,
            key = { index -> transactions[index].month }) { index ->
            val transactionInMonth = transactions[index]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = if (index == 0) 48.dp else 8.dp)

            ) {
                Text(
                    text = transactionInMonth.month,
                    fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                )

                transactionInMonth.transactions.forEach { transaction ->
                    key(transaction.transactionEntity.id) {
                        TransactionItem(
                            title = transaction.transactionEntity.title,
                            date = transaction.transactionEntity.date,
                            category = transaction.categoryEntity.name,
                            amount = transaction.transactionEntity.amount,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}



