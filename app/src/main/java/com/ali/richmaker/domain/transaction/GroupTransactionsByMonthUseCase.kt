package com.ali.richmaker.domain.transaction

import com.ali.richmaker.data.local.database.model.TransactionWithCategoryModel
import com.ali.richmaker.data.local.database.model.TransactionsInMonthModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GroupTransactionsByMonthUseCase @Inject constructor(
    private val monthFormatter: DateTimeFormatter
) {
    operator fun invoke(transactions: List<TransactionWithCategoryModel>): List<TransactionsInMonthModel> {
        return transactions.groupBy { transaction ->
            transaction.transactionEntity.date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate().format(monthFormatter)
        }.map { (month, transactions) ->
            TransactionsInMonthModel(
                month = month, transactions = transactions
            )
        }
    }
}