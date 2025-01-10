package com.ali.richmaker.domain

import com.ali.richmaker.data.local.database.dao.CategoryDao
import javax.inject.Inject

class GetAddCategoriesUseCase @Inject constructor(
    private val categoryDao: CategoryDao
) {

    operator fun invoke() = categoryDao.getAllCategories()
}