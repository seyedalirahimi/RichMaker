package com.ali.richmaker.domain.transaction

import com.ali.richmaker.data.TransactionRepository
import javax.inject.Inject

class GetAllTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke() = transactionRepository.getAllTransactions()

}