package com.ali.richmaker.data

import com.ali.richmaker.common.Dispatcher
import com.ali.richmaker.common.RichMakerDispatchers
import com.ali.richmaker.data.local.database.TransactionDao
import com.ali.richmaker.data.local.database.TransactionEntity
import com.ali.richmaker.data.local.database.TransactionWithCategory
import com.ali.richmaker.data.local.database.TransactionsInMonth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

interface TransactionRepository {
    suspend fun upsertTransaction(transaction: TransactionEntity)
    fun getTransactionsWithCategories(): Flow<List<TransactionWithCategory>>
    fun getTotalIncome(): Flow<Double>
    fun getTotalExpense(): Flow<Double>
    fun getBalance(): Flow<Double>
    fun getTransactionsGroupedByMonth(): Flow<List<TransactionsInMonth>>
}


class DefaultTransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    @Dispatcher(RichMakerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TransactionRepository {
    private val monthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")

    override suspend fun upsertTransaction(transaction: TransactionEntity) {
        withContext(ioDispatcher) {
            transactionDao.upsertTransaction(transaction)
        }
    }

    override fun getTransactionsWithCategories(): Flow<List<TransactionWithCategory>> {
        return transactionDao.getTransactionsWithCategories()

    }

    override fun getTotalIncome(): Flow<Double> {
        return transactionDao.getTotalIncome()

    }

    override fun getTotalExpense(): Flow<Double> {
        return transactionDao.getTotalExpense()

    }

    override fun getBalance(): Flow<Double> {
        return transactionDao.getBalance()

    }

    override fun getTransactionsGroupedByMonth(): Flow<List<TransactionsInMonth>> {
        return transactionDao.getTransactionsWithCategories().map { transactions ->
            transactions.groupBy { transaction ->
                transaction.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    .format(monthFormatter)
            }.map { (month, transactionsInMonth) ->
                TransactionsInMonth(
                    month = month, transactions = transactionsInMonth
                )
            }.sortedByDescending { it.month }
        }
    }
}