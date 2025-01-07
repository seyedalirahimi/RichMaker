package com.ali.richmaker.data.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ali.richmaker.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase) = appDatabase.transactionDao()

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase) = appDatabase.categoryDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {

        val predefinedCategories = listOf(
            "Salary", "Food", "Transportation", "Entertainment", "Healthcare", "Education", "Other"
        )
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    predefinedCategories.forEach { categoryName ->
                        db.execSQL("INSERT INTO categories (name, isPredefined) VALUES ('$categoryName', 1)")
                    }
                }
            }
        }).build()
    }
}