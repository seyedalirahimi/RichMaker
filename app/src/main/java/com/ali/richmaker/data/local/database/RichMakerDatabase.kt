package com.ali.richmaker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ali.richmaker.data.local.database.dao.CategoryDao
import com.ali.richmaker.data.local.database.dao.TransactionDao
import com.ali.richmaker.data.local.database.model.CategoryEntity
import com.ali.richmaker.data.local.database.model.TransactionEntity
import com.ali.richmaker.data.local.database.util.TypeConverter

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(TypeConverter::class)
abstract class RichMakerDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "rich-maker.db"
    }

}