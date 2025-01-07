package com.ali.richmaker.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert


@Entity(tableName = "categories")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val isPredefined: Boolean // True for predefined categories, false for user-added
)


@Dao
interface CategoryDao {
    @Upsert
    suspend fun upsertCategory(category: CategoryModel): Long

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryModel>

    @Query("SELECT * FROM categories WHERE isPredefined = 1")
    suspend fun getPredefinedCategories(): List<CategoryModel>

    @Query("SELECT * FROM categories WHERE isPredefined = 0")
    suspend fun getUserCategories(): List<CategoryModel>
}