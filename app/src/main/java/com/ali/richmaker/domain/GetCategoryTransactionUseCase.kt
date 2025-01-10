package com.ali.richmaker.domain

import com.ali.richmaker.data.TransactionRepository
import com.ali.richmaker.data.local.database.model.TransactionWithCategoryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsByCategoryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(categoryId: Int): Flow<List<TransactionWithCategoryModel>> {
        return transactionRepository.getTransactionsByCategory(categoryId)
    }

}