package com.ali.richmaker.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataTimeFormatterModule {

    @Provides
    @Singleton
    fun provideMonthFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("MMMM yyyy")
    }
}