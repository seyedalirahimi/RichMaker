package com.ali.richmaker.ui.categories

import androidx.lifecycle.ViewModel
import com.ali.richmaker.data.local.database.dao.CategoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryDao: CategoryDao,
) : ViewModel() {

}