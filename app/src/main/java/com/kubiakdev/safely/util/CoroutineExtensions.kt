package com.kubiakdev.safely.util

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

fun <T> asyncExecutor(
        context: CoroutineContext = UI,
        block: () -> T,
        response: (T) -> Unit): Job {
    return launch(context) {
        val deferred = async(DefaultDispatcher) { block.invoke() }
        response.invoke(deferred.await())
    }
}

fun <T> asyncExecutor(
        context: CoroutineContext = UI,
        response: (List<T>) -> Unit,
        vararg blocks: () -> T): Job {
    return launch(context) {
        val deferredTypeArray = mutableListOf<Deferred<T>>()
        blocks.forEach { deferredTypeArray.add(async(DefaultDispatcher) { it.invoke() }) }
        response.invoke(deferredTypeArray.awaitAll())
    }
}


// UI,                                       launch on UI only for Android
// DefaultDispatcher                         launch on default dispatcher
// Dispatchers.Default                       i don't care. Launch wherever you want
// newSingleThreadContext("My context")      launch on my own context
