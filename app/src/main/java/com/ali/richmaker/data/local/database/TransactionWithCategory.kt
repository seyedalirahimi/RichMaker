package com.ali.richmaker.data.local.database

import java.util.Date

data class TransactionWithCategory(
    val id: Int,
    val date: Date,
    val categoryName: String,
    val amount: Double,
    val title: String,
    val message: String?,
    val isIncome: Boolean
)
