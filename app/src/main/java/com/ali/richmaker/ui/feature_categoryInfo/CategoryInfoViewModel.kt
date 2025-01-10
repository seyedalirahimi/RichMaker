package com.ali.richmaker.ui.feature_categoryInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ali.richmaker.data.local.database.model.TransactionWithCategoryModel
import com.ali.richmaker.data.local.database.model.TransactionsInMonthModel
import com.ali.richmaker.domain.GetTransactionsByCategoryUseCase
import com.ali.richmaker.ui.feature_transactions.TransactionsUiState
import com.ali.richmaker.ui.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTransactionsByCategoryUseCase: GetTransactionsByCategoryUseCase
) : ViewModel() {

    val categoryId: Int = savedStateHandle.toRoute<Route.CategoryInfoRoute>().categoryId

    private val _state: MutableStateFlow<CategoryInfoUiState> =
        MutableStateFlow(CategoryInfoUiState())
    val state: StateFlow<CategoryInfoUiState> get() = _state

    init {
        viewModelScope.launch {
            getTransactionsByCategoryUseCase(categoryId).collect { transactions ->
                _state.update {
                    it.copy(
                        transactions = transactions
                    )
                }
            }

        }
    }
}

data class CategoryInfoUiState(
    val transactions: List<TransactionWithCategoryModel> = emptyList(),
)