package com.kubiakdev.safely.ui.main

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.entityToModel
import com.kubiakdev.safely.data.model.PasswordModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import javax.inject.Inject

class MainViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private val dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    fun remove(id: Long, doAfterRemove: () -> Unit): Job = launchCatching(
            action = { dataManager.passwordBox.remove(id) },
            onSuccess = { doAfterRemove() }
    )

    fun getPasswords(response: (List<PasswordModel>) -> Unit): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = { dataManager.passwordBox.all.map { entityToModel(it) } },
            onSuccess = { response(it) }
    )

    fun switch(fromId: Long, toId: Long, doAfterSwitch: () -> Unit): Job = launchCatching(
            action = {
                dataManager.passwordBox.apply {
                    val firstRecord = get(fromId)
                    val secondRecord = get(toId)
                    val tempFirstRecord = get(fromId)

                    put(
                            firstRecord.apply { id = secondRecord.id },
                            secondRecord.apply { id = tempFirstRecord.id }
                    )
                }
            },
            onSuccess = { doAfterSwitch() }
    )
}