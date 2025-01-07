package com.ali.richmaker.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.richmaker.data.local.database.dao.CategoryDao
import com.ali.richmaker.data.local.database.model.CategoryEntity
import com.ali.richmaker.ui.transactions.TransactionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    categoryDao: CategoryDao,
) : ViewModel() {
    val state: StateFlow<CategoriesUiState> =
        categoryDao.getAllCategories().map { categories ->
            CategoriesUiState(categories = categories)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CategoriesUiState()
        )


}

data class CategoriesUiState(
    val categories: List<CategoryEntity> = emptyList(),

    )