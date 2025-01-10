package com.ali.richmaker.data.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val isIncome: Boolean = true,
    val isPredefined: Boolean // True for predefined categories, false for user-added
)

