package com.ali.richmaker.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.richmaker.data.TransactionRepository
import com.ali.richmaker.data.local.database.TransactionWithCategory
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state: MutableStateFlow<TransactionsUiState> =
        MutableStateFlow(TransactionsUiState())
    val state: StateFlow<TransactionsUiState> get() = _state

    init {
        viewModelScope.launch {

            val groupedTransactions = transactionRepository.getTransactionsGroupedByMonth()
            val totalIncome = transactionRepository.getTotalIncome()
            val totalExpense = transactionRepository.getTotalExpense()
            val balance = transactionRepository.getBalance()

            _state.update {
                TransactionsUiState(
                    transactions = groupedTransactions.map { (month, transactions) ->
                        TransactionInMonth(month, transactions)
                    },
                    totalBalance = balance,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense
                )
            }
        }
    }
}


data class TransactionsUiState(
    val transactions: List<TransactionInMonth> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0
)


data class TransactionInMonth(
    val month: String, val transactions: List<TransactionWithCategory>
)