package com.ali.richmaker.data.di

import com.ali.richmaker.data.DefaultTransactionRepository
import com.ali.richmaker.data.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindTransactionRepository(impl: DefaultTransactionRepository): TransactionRepository



}