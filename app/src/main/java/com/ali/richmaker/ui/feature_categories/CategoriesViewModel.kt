package com.ali.richmaker.ui.feature_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.richmaker.data.local.database.model.CategoryEntity
import com.ali.richmaker.domain.category.GetAddCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    getAddCategoriesUseCase: GetAddCategoriesUseCase,
) : ViewModel() {
    val state: StateFlow<CategoriesUiState> =
        getAddCategoriesUseCase().map { categories ->
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