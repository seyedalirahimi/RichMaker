package com.ali.richmaker.data.local.database.model

data class TransactionsInMonthModel(
    val month: String,
    val transactions: List<TransactionWithCategoryModel>
)