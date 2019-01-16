package com.kubiakdev.safely.base

import androidx.lifecycle.ViewModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
        open val coroutineContextProvider: CoroutineContextProvider
) : ViewModel(), CoroutineScope {

    private val parentJob = Job()

    override val coroutineContext = Dispatchers.Main + parentJob

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }

    protected fun <T> launchCatching(
            executionContext: CoroutineContext = coroutineContextProvider.io,
            action: () -> T,
            onSuccess: (value: T) -> Unit = {},
            onFailure: (value: Throwable) -> Unit = { throw Exception(it) },
            onSuccessContext: CoroutineContext = coroutineContextProvider.main,
            onFailureContext: CoroutineContext = coroutineContextProvider.main
    ): Job = launch(executionContext) {
        Result.runCatching {
            action()
        }
                .onSuccess {
                    if (onSuccessContext == executionContext) {
                        onSuccess(it)
                    } else {
                        withContext(onSuccessContext) {
                            onSuccess(it)
                        }
                    }
                }
                .onFailure {
                    if (onFailureContext == executionContext) {
                        onFailure(it)
                    } else {
                        withContext(onFailureContext) {
                            onFailure(it)
                        }
                    }
                }
    }

}