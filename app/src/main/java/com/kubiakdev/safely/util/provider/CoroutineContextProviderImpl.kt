package com.kubiakdev.safely.util.provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CoroutineContextProviderImpl : CoroutineContextProvider {

    override val default: CoroutineDispatcher = Dispatchers.Default

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val main: CoroutineDispatcher = Dispatchers.Main

    @ExperimentalCoroutinesApi
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined

}