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
    fun getAllTransactions(): Flow<List<TransactionWithCategoryModel>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE amount >= 0")
    fun getAllIncomeTransactions(): Flow<List<TransactionWithCategoryModel>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE amount < 0")
    fun getAllExpenseTransactions(): Flow<List<TransactionWithCategoryModel>>


    @Transaction
    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId")
    fun getTransactionsByCategory(categoryId: Int): Flow<List<TransactionWithCategoryModel>>


    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE amount >= 0")
    fun getTotalIncome(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE amount < 0")
    fun getTotalExpense(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions")
    fun getBalance(): Flow<Double>
}