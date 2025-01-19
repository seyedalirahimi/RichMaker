package com.ali.richmaker.domain.category

import com.ali.richmaker.data.local.database.dao.CategoryDao
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoryDao: CategoryDao
) {
    operator fun invoke() = categoryDao.getAllCategories()
}