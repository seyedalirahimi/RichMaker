package com.ali.richmaker.data.local.database

data class TransactionsInMonth(
    val month: String,
    val transactions: List<TransactionWithCategory>
)