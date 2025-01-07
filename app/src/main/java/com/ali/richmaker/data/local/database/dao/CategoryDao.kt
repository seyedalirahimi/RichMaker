package com.ali.richmaker.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ali.richmaker.data.local.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {
    @Upsert
    suspend fun upsertCategory(category: CategoryEntity): Long

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE isPredefined = 1")
    fun getPredefinedCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE isPredefined = 0")
    fun getUserCategories(): Flow<List<CategoryEntity>>
}