package com.ali.richmaker.data.local.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithCategoryModel(
    @Embedded val transactionEntity: TransactionEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val categoryEntity: CategoryEntity
)
