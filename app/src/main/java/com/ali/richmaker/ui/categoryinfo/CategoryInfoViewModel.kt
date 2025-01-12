package com.ali.richmaker.ui.categoryinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ali.richmaker.data.local.database.model.TransactionWithCategoryModel
import com.ali.richmaker.domain.category.GetCategoryByIdUseCase
import com.ali.richmaker.domain.category.GetTransactionsByCategoryUseCase
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
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    getTransactionsByCategoryUseCase: GetTransactionsByCategoryUseCase
) : ViewModel() {

    val categoryId: Int = savedStateHandle.toRoute<Route.CategoryInfoRoute>().categoryId

    private val _state: MutableStateFlow<CategoryInfoUiState> =
        MutableStateFlow(CategoryInfoUiState())
    val state: StateFlow<CategoryInfoUiState> get() = _state

    init {
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
                    it.copy(
                        transactions = transactions
                    )
                }
            }

        }
    }
}

data class CategoryInfoUiState(
    val categoryName: String = "",
    val transactions: List<TransactionWithCategoryModel> = emptyList(),
)