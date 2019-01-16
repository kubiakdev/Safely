package com.kubiakdev.safely.util.provider

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineContextProvider {

    val default: CoroutineDispatcher

    val io: CoroutineDispatcher

    val main: CoroutineDispatcher

    val unconfined: CoroutineDispatcher

}