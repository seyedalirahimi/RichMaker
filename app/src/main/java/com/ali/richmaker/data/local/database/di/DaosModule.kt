package com.ali.richmaker.data.local.database.di

import com.ali.richmaker.data.local.database.RichMakerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun provideTransactionDao(appDatabase: RichMakerDatabase) = appDatabase.transactionDao()

    @Provides
    fun provideCategoryDao(richMakerDatabase: RichMakerDatabase) = richMakerDatabase.categoryDao()

}