package com.ali.richmaker.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.richmaker.data.TransactionRepository
import com.ali.richmaker.data.local.database.model.TransactionWithCategoryModel
import com.ali.richmaker.data.local.database.model.TransactionsInMonthModel
import com.ali.richmaker.domain.transaction.GetAllExposeTransactionUseCase
import com.ali.richmaker.domain.transaction.GetAllIncomeTransactionUseCase
import com.ali.richmaker.domain.transaction.GetAllTransactionUseCase
import com.ali.richmaker.domain.transaction.GroupTransactionsByMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getAllTransactionUseCase: GetAllTransactionUseCase,
    private val getAllIncomeTransactionUseCase: GetAllIncomeTransactionUseCase,
    private val getAllExposeTransactionUseCase: GetAllExposeTransactionUseCase,
    private val groupTransactionsByMonthUseCase: GroupTransactionsByMonthUseCase,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state: MutableStateFlow<TransactionsUiState> =
        MutableStateFlow(TransactionsUiState())
    val state: StateFlow<TransactionsUiState> get() = _state

    init {
        observeBalance()
        observeIncome()
        observeExpense()
        loadAllTransactions()
    }

    // Observers
    private fun observeBalance() = viewModelScope.launch {
        transactionRepository.getBalance().collect { balance ->
            updateState { copy(totalBalance = balance) }
        }
    }

    private fun observeIncome() = viewModelScope.launch {
        transactionRepository.getIncome().collect { income ->
            updateState { copy(totalIncome = income) }
        }
    }

    private fun observeExpense() = viewModelScope.launch {
        transactionRepository.getExpense().collect { expense ->
            updateState { copy(totalExpense = expense) }
        }
    }

    // Loaders
    private fun loadAllTransactions() = viewModelScope.launch {
        handleTransactionFetching { getAllTransactionUseCase() }
    }

    private fun loadIncomeTransactions() = viewModelScope.launch {
        handleTransactionFetching { getAllIncomeTransactionUseCase() }
    }

    private fun loadExpenseTransactions() = viewModelScope.launch {
        handleTransactionFetching { getAllExposeTransactionUseCase() }
    }

    private suspend fun handleTransactionFetching(useCase: suspend () -> Flow<List<TransactionWithCategoryModel>>) {
        useCase().collect { transactions ->
            val groupedTransactions = groupTransactionsByMonthUseCase(transactions)
            updateState { copy(transactions = groupedTransactions) }
        }
    }

    // Event Handler
    fun onEvent(event: TransactionEvent) {
        when (event) {
            TransactionEvent.OnBalanceClick -> {
                updateFilterMode(TransactionsFilterMode.Balance)
                loadAllTransactions()
            }

            TransactionEvent.OnIncomeClick -> {
                updateFilterMode(TransactionsFilterMode.Income)
                loadIncomeTransactions()
            }

            TransactionEvent.OnExpenseClick -> {
                updateFilterMode(TransactionsFilterMode.Expense)
                loadExpenseTransactions()
            }
        }
    }

    // Helper Functions
    private fun updateFilterMode(filterMode: TransactionsFilterMode) {
        updateState { copy(transactionsFilterMode = filterMode, isLoading = true) }
    }

    private fun updateState(update: TransactionsUiState.() -> TransactionsUiState) {
        _state.update(update)
    }
}

// UI State
data class TransactionsUiState(
    // fixme: add pagination for items
    val transactionsFilterMode: TransactionsFilterMode = TransactionsFilterMode.Balance,
    val transactions: List<TransactionsInMonthModel> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val isLoading: Boolean = false,
)

// Events
sealed interface TransactionEvent {
    data object OnBalanceClick : TransactionEvent
    data object OnIncomeClick : TransactionEvent
    data object OnExpenseClick : TransactionEvent
}

// Filter Modes
sealed interface TransactionsFilterMode {
    data object Balance : TransactionsFilterMode
    data object Income : TransactionsFilterMode
    data object Expense : TransactionsFilterMode
}
