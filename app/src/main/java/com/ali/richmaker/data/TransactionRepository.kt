package com.ali.richmaker.data

import com.ali.richmaker.common.Dispatcher
import com.ali.richmaker.common.RichMakerDispatchers
import com.ali.richmaker.data.local.database.dao.TransactionDao
import com.ali.richmaker.data.local.database.model.TransactionEntity
import com.ali.richmaker.data.local.database.model.TransactionWithCategoryModel
import com.ali.richmaker.data.local.database.model.TransactionsInMonthModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface TransactionRepository {
    suspend fun upsertTransaction(transaction: TransactionEntity)
    fun getAllTransactions(): Flow<List<TransactionWithCategoryModel>>
    fun getAllIncomeTransactions(): Flow<List<TransactionWithCategoryModel>>
    fun getAllExpenseTransactions(): Flow<List<TransactionWithCategoryModel>>
    fun getTransactionsByCategory(categoryId: Int): Flow<List<TransactionWithCategoryModel>>
    fun getIncome(): Flow<Double>
    fun getExpense(): Flow<Double>
    fun getBalance(): Flow<Double>
}


class DefaultTransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    @Dispatcher(RichMakerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TransactionRepository {

    override suspend fun upsertTransaction(transaction: TransactionEntity) {
        withContext(ioDispatcher) {
            transactionDao.upsertTransaction(transaction)
        }
    }

    override fun getAllTransactions(): Flow<List<TransactionWithCategoryModel>> {
        return transactionDao.getAllTransactions()

    }

    override fun getAllIncomeTransactions(): Flow<List<TransactionWithCategoryModel>> {
        return transactionDao.getAllIncomeTransactions()
    }

    override fun getAllExpenseTransactions(): Flow<List<TransactionWithCategoryModel>> {
        return transactionDao.getAllExpenseTransactions()
    }

    override fun getTransactionsByCategory(categoryId: Int): Flow<List<TransactionWithCategoryModel>> {
        return transactionDao.getTransactionsByCategory(categoryId)
    }

    override fun getIncome(): Flow<Double> {
        return transactionDao.getTotalIncome()

    }

    override fun getExpense(): Flow<Double> {
        return transactionDao.getTotalExpense()

    }

    override fun getBalance(): Flow<Double> {
        return transactionDao.getBalance()

    }
}