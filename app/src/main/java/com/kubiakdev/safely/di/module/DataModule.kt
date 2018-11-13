package com.kubiakdev.safely.di.module

import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.db.DbManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDataManager(): DataManager = DbManager()
}