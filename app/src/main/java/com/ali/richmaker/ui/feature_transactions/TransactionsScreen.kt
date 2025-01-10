package com.ali.richmaker.ui.feature_transactions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionsRoute(
    viewModel: TransactionsViewModel = hiltViewModel(), modifier: Modifier = Modifier
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    TransactionsScreen(
        uiState = uiState.value,
        onBalanceClick = { viewModel.onEvent(TransactionEvent.OnBalanceClick) },
        onIncomeClick = { viewModel.onEvent(TransactionEvent.OnIncomeClick) },
        onExpenseClick = { viewModel.onEvent(TransactionEvent.OnExpenseClick) },
        modifier = modifier,
    )
}


@Composable
fun TransactionsScreen(
    uiState: TransactionsUiState = TransactionsUiState(),
    onBalanceClick: () -> Unit = {},
    onIncomeClick: () -> Unit = {},
    onExpenseClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {

        Column(modifier = Modifier.padding(16.dp)) {
            TextButton(
                onClick = onBalanceClick
            ) {
                Text(
                    color = if (uiState.transactionsFilterMode == TransactionsFilterMode.Balance) Color.Blue else Color.Black,
                    text = "Balance: ${uiState.totalBalance.let { "$$it" }}"
                )
            }

            TextButton(
                onClick = onIncomeClick
            ) {
                Text(
                    color = if (uiState.transactionsFilterMode == TransactionsFilterMode.Income) Color.Blue else Color.Black,
                    text = "Income: ${uiState.totalIncome.let { "$$it" }}"
                )
            }

            TextButton(
                onClick = onExpenseClick
            ) {
                Text(
                    color = if (uiState.transactionsFilterMode == TransactionsFilterMode.Expense) Color.Blue else Color.Black,
                    text = "Expense: ${uiState.totalExpense.let { "$$it" }}"
                )
            }

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
                                TransactionItem(
                                    imageResourceId = R.drawable.ic_profile,
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
}

@Composable
fun TransactionItem(
    @DrawableRes imageResourceId: Int,
    title: String,
    date: Date,
    category: String,
    amount: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color(0xFFEFF8F1)) // Background color
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Section
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF5B8FF9), CircleShape), // Icon background
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(imageResourceId),
                contentDescription = null,
                modifier = modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Title and Date Section
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Text(
                text = formatDateToCustomFormat(date),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = Color(0xFF5B8FF9) // Light blue
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Category and Amount Section
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = Color.Gray
            )
            Text(
                text = String.format(Locale.US, "%.2f", amount),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (amount > 0) Color.Black else Color(0xFF5B8FF9) // Black for income, Blue for expense
                ),
                textAlign = TextAlign.End
            )
        }
    }
}

fun formatDateToCustomFormat(date: Date): String {
    val dateFormat = SimpleDateFormat("HH:mm - MMMM dd", Locale.getDefault())
    return dateFormat.format(date)
}


