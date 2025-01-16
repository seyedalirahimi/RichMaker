package com.ali.richmaker.ui.transactions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.R
import com.ali.richmaker.common.designsystem.component.CurrencyText
import com.ali.richmaker.common.designsystem.component.FinancialIconButton
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

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.tertiary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Transactions",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.size(8.dp))

        Column(
            modifier = modifier.padding(
                horizontal = 16.dp, vertical = 8.dp
            )
        ) {
            FinancialIconButton(
                label = "Total Balance",
                isEnable = uiState.transactionsFilterMode == TransactionsFilterMode.Balance,
                amount = uiState.totalBalance,
                hasIcon = false,
                modifier = modifier.fillMaxWidth(),
                onClick = onBalanceClick
                )

            Spacer(modifier = modifier.size(12.dp))

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
        if (uiState.transactions.isEmpty()) {
            Text(text = "No transactions available.")
        } else {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
                    )
                    .padding(
                        horizontal = 16.dp, vertical = 8.dp
                    )
            ) {
                LazyColumn(
                    modifier = Modifier.padding(8.dp),
                ) {
                    uiState.transactions.forEach { transactionInMonth ->
                        item {
                            Text(
                                fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                                text = transactionInMonth.month,
                                modifier = Modifier.padding(8.dp)
                            )
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
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(percent = 35)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(imageResourceId), tint = Color.White,
                contentDescription = null,
                modifier = modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Column {
            Text(
                text = title, style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = formatDateToCustomFormat(date),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        VerticalDivider(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.weight(1f))

        CurrencyText(
            amount = amount,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier

        )
    }
}

fun formatDateToCustomFormat(date: Date): String {
    val dateFormat = SimpleDateFormat("HH:mm - MMMM dd", Locale.getDefault())
    return dateFormat.format(date)
}


