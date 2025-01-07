package com.ali.richmaker.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.richmaker.data.TransactionRepository
import com.ali.richmaker.data.local.database.model.TransactionsInMonthModel
import dagger.hilt.android.lifecycle.HiltViewModel
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


        viewModelScope.launch {
            transactionRepository.getTransactionsGroupedByMonth().collect { transactions ->
                _state.update { it.copy(transactions = transactions) }
            }
        }
        viewModelScope.launch {
            transactionRepository.getBalance().collect { balance ->
                _state.update { it.copy(totalBalance = balance) }
            }
        }
        viewModelScope.launch {
            transactionRepository.getTotalIncome().collect { income ->
                _state.update { it.copy(totalIncome = income) }
            }
        }
        viewModelScope.launch {
            transactionRepository.getTotalExpense().collect { expense ->
                _state.update { it.copy(totalExpense = expense) }
            }
        }
    }

}

data class TransactionsUiState(
    val transactions: List<TransactionsInMonthModel> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val isLoading: Boolean = false,
)

