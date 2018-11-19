package com.kubiakdev.safely.ui.main

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.entityToModel
import com.kubiakdev.safely.data.mapper.modelToEntity
import com.kubiakdev.safely.data.model.PasswordModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import javax.inject.Inject

class MainViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private val dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    fun updateDatabase(data: List<PasswordModel>): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = {
                dataManager.allPasswordEntities = data.map { modelToEntity(it) }.toMutableList()
            }
    )

    fun getData(response: (List<PasswordModel>) -> Unit): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = { dataManager.allPasswordEntities.map { entityToModel(it) } },
            onSuccess = { response(it) }
    )
}