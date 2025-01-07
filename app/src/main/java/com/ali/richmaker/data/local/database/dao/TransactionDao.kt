package com.ali.richmaker.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.ali.richmaker.data.local.database.model.TransactionWithCategoryModel
import com.ali.richmaker.data.local.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsertTransaction(transaction: TransactionEntity)

    @Transaction
    @Query("SELECT * FROM transactions")
    fun getTransactionsWithCategory(): Flow<List<TransactionWithCategoryModel>>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE isIncome = 1")
    fun getTotalIncome(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE isIncome = 0")
    fun getTotalExpense(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions")
    fun getBalance(): Flow<Double>
}