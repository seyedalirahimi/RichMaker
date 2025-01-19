package com.ali.richmaker.ui.categoryinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ali.richmaker.data.TransactionRepository
import com.ali.richmaker.data.local.database.model.TransactionsInMonthModel
import com.ali.richmaker.domain.category.GetCategoryByIdUseCase
import com.ali.richmaker.domain.category.GetTransactionsByCategoryUseCase
import com.ali.richmaker.domain.transaction.GroupTransactionsByMonthUseCase
import com.ali.richmaker.ui.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    transactionRepository: TransactionRepository,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val groupTransactionsByMonthUseCase: GroupTransactionsByMonthUseCase,
    getTransactionsByCategoryUseCase: GetTransactionsByCategoryUseCase
) : ViewModel() {

    val categoryId: Int = savedStateHandle.toRoute<Route.CategoryInfoRoute>().categoryId

    private val _state: MutableStateFlow<CategoryInfoUiState> =
        MutableStateFlow(CategoryInfoUiState())
    val state: StateFlow<CategoryInfoUiState> get() = _state

    init {
        _state.value = _state.value.copy(
            goal = 200_000.0
        )

        viewModelScope.launch {
            transactionRepository.getBalance().collect { balance ->
                _state.value = _state.value.copy(totalBalance = balance)
            }
        }
        viewModelScope.launch {
            transactionRepository.getExpense().collect { expense ->
                _state.value = _state.value.copy(totalExpense = expense)
            }
        }
        viewModelScope.launch {
            getCategoryByIdUseCase(categoryId).collectLatest { category ->
                _state.update {
                    it.copy(
                        categoryName = category.name
                    )
                }
            }
        }

        viewModelScope.launch {
            getTransactionsByCategoryUseCase(categoryId).collect { transactions ->
                _state.update {
                    val groupedTransactions = groupTransactionsByMonthUseCase(transactions)
                    it.copy(
                        transactions = groupedTransactions
                    )
                }
            }

        }
    }
}

data class CategoryInfoUiState(
    val categoryName: String = "",
    val transactions: List<TransactionsInMonthModel> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalExpense: Double = 0.0,
    val goal: Double = 0.0,
)