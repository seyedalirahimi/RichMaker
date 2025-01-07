package com.ali.richmaker.data.di

import com.ali.richmaker.data.DefaultTransactionRepository
import com.ali.richmaker.data.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindTransactionRepository(impl: DefaultTransactionRepository): TransactionRepository



}