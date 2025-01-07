package com.ali.richmaker.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import java.util.Date

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryModel::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Date,
    val categoryId: Int,
    val amount: Double,
    val title: String,
    val message: String? = null,
    val isIncome: Boolean
)


@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsertTransaction(transaction: TransactionEntity)

    @Query(
        """
        SELECT t.*, c.name AS categoryName 
        FROM transactions t 
        JOIN categories c ON t.categoryId = c.id
        """
    )
    suspend fun getTransactionsWithCategories(): List<TransactionWithCategory>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE isIncome = 1")
    fun getTotalIncome(): Double

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE isIncome = 0")
    fun getTotalExpense(): Double

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions")
    fun getBalance(): Double
}