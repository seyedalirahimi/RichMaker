package com.ali.richmaker.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.richmaker.data.TransactionRepository
import com.ali.richmaker.data.local.database.model.CategoryEntity
import com.ali.richmaker.domain.category.GetAddCategoriesUseCase
import com.ali.richmaker.ui.transactions.TransactionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    getAddCategoriesUseCase: GetAddCategoriesUseCase,
    transactionRepository: TransactionRepository

) : ViewModel() {

    private val _state: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState())
    val state: StateFlow<CategoriesUiState> get() = _state

    init {
        _state.value = _state.value.copy(
            goal = 10000.0
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
            getAddCategoriesUseCase().collect { categories ->
                _state.value = _state.value.copy(categories = categories)
            }
        }
    }


}

data class CategoriesUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalExpense: Double = 0.0,
    val goal: Double = 0.0,
    )