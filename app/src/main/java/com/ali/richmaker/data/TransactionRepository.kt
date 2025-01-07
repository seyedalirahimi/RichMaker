package com.ali.richmaker.data

import com.ali.richmaker.data.local.database.CategoryDao
import com.ali.richmaker.data.local.database.TransactionDao
import com.ali.richmaker.data.local.database.TransactionEntity
import com.ali.richmaker.data.local.database.TransactionWithCategory
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

interface TransactionRepository {
    suspend fun upsertTransaction(transaction: TransactionEntity)
    suspend fun getTransactionsWithCategories(): List<TransactionWithCategory>
    suspend fun getTotalIncome(): Double
    suspend fun getTotalExpense(): Double
    suspend fun getBalance(): Double
    suspend fun getTransactionsGroupedByMonth(): Map<String, List<TransactionWithCategory>>
}


class DefaultTransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    private val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    override suspend fun upsertTransaction(transaction: TransactionEntity) {
        transactionDao.upsertTransaction(transaction)
    }

    override suspend fun getTransactionsWithCategories(): List<TransactionWithCategory> {
        return transactionDao.getTransactionsWithCategories()
    }

    override suspend fun getTotalIncome(): Double {
        return transactionDao.getTotalIncome()
    }

    override suspend fun getTotalExpense(): Double {
        return transactionDao.getTotalExpense()
    }

    override suspend fun getBalance(): Double {
        return transactionDao.getBalance()
    }

    override suspend fun getTransactionsGroupedByMonth(): Map<String, List<TransactionWithCategory>> {
        return transactionDao.getTransactionsWithCategories().groupBy { dateFormat.format(it.date) }
    }
}