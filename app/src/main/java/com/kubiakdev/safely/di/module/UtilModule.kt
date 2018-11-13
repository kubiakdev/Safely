package com.kubiakdev.safely.di.module

import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import com.kubiakdev.safely.util.provider.CoroutineContextProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Singleton
    @Provides
    fun provideCoroutineContext(): CoroutineContextProvider = CoroutineContextProviderImpl()
}