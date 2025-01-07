package com.ali.richmaker.data

import com.ali.richmaker.common.Dispatcher
import com.ali.richmaker.common.RichMakerDispatchers
import com.ali.richmaker.data.local.database.TransactionDao
import com.ali.richmaker.data.local.database.TransactionEntity
import com.ali.richmaker.data.local.database.TransactionWithCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
    private val transactionDao: TransactionDao,
    @Dispatcher(RichMakerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TransactionRepository {
    private val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    override suspend fun upsertTransaction(transaction: TransactionEntity) {
        withContext(ioDispatcher) {
            transactionDao.upsertTransaction(transaction)
        }
    }

    override suspend fun getTransactionsWithCategories(): List<TransactionWithCategory> {
        return withContext(ioDispatcher) {
            transactionDao.getTransactionsWithCategories()
        }
    }

    override suspend fun getTotalIncome(): Double {
        return withContext(ioDispatcher) {
            transactionDao.getTotalIncome()
        }
    }

    override suspend fun getTotalExpense(): Double {
        return withContext(ioDispatcher) {
            transactionDao.getTotalExpense()
        }
    }

    override suspend fun getBalance(): Double {
        return withContext(ioDispatcher) {
            transactionDao.getBalance()
        }
    }

    override suspend fun getTransactionsGroupedByMonth(): Map<String, List<TransactionWithCategory>> {
        return withContext(ioDispatcher) {
            transactionDao.getTransactionsWithCategories()
                .groupBy { dateFormat.format(it.date) }
        }
    }
}