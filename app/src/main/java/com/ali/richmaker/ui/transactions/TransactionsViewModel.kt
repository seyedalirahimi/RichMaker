package com.ali.richmaker.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.richmaker.data.TransactionRepository
import com.ali.richmaker.data.local.database.TransactionWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state: MutableStateFlow<TransactionsUiState> =
        MutableStateFlow(TransactionsUiState())
    val state: StateFlow<TransactionsUiState> get() = _state

    init {
        loadTransactionsData()
    }

    private fun loadTransactionsData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                // Fetch data in parallel using async
                val groupedTransactionsDeferred =
                    async { transactionRepository.getTransactionsGroupedByMonth() }
                val totalIncomeDeferred = async { transactionRepository.getTotalIncome() }
                val totalExpenseDeferred = async { transactionRepository.getTotalExpense() }
                val balanceDeferred = async { transactionRepository.getBalance() }

                // Await all data
                val groupedTransactions = groupedTransactionsDeferred.await()
                val totalIncome = totalIncomeDeferred.await()
                val totalExpense = totalExpenseDeferred.await()
                val balance = balanceDeferred.await()

                _state.update {
                    it.copy(
                        transactions = groupedTransactions.map { (month, transactions) ->
                            TransactionInMonth(month, transactions)
                        },
                        totalBalance = balance,
                        totalIncome = totalIncome,
                        totalExpense = totalExpense,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // Handle error and update state
                _state.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "An error occurred")
                }
            }
        }
    }
}

data class TransactionsUiState(
    val transactions: List<TransactionInMonth> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val isLoading: Boolean = false, // Added loading state
    val errorMessage: String? = null // Added error message
)

data class TransactionInMonth(
    val month: String, val transactions: List<TransactionWithCategory>
)
