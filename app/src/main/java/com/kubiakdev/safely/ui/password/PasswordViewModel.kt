package com.kubiakdev.safely.ui.password

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.mapJsonToDetailList
import com.kubiakdev.safely.data.mapper.modelToEntity
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.data.model.PasswordModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import javax.inject.Inject

class PasswordViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private val dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    fun getPassword(id: Long, doWithList: (List<DetailModel>) -> Unit) = launchCatching(
            action = { mapJsonToDetailList(dataManager.passwordBox[id].jsonDetailList) },
            onSuccess = { doWithList(it) }
    )

    fun addPassword(model: PasswordModel, doAfterAdd: (PasswordModel) -> Unit) = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = { dataManager.passwordBox.put(modelToEntity(model)) },
            onSuccess = { doAfterAdd(model.apply { id = it }) }
    )

    fun updatePassword(model: PasswordModel, doAfterAdd: () -> Unit) = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = { dataManager.passwordBox.put(modelToEntity(model)) },
            onSuccess = { doAfterAdd() }
    )

    fun remove(id: Long, doAfterRemove: () -> Unit): Job = launchCatching(
            action = { dataManager.templateBox.remove(id) },
            onSuccess = { doAfterRemove() }
    )

    fun switch(fromId: Long, toId: Long, doAfterSwitch: () -> Unit): Job = launchCatching(
            action = {
                dataManager.detailBox.apply {
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