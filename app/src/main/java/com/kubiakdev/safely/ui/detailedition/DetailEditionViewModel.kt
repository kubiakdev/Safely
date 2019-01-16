package com.kubiakdev.safely.ui.detailedition

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.modelToEntity
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import javax.inject.Inject

class DetailEditionViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private val dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    fun addModel(
            model: TemplateModel,
            actionAfterEdit: (TemplateModel) -> Unit
    ): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = { model.apply { dataManager.templateBox.put(modelToEntity(this)) } },
            onSuccess = { actionAfterEdit(it) }
    )

    fun editModel(
            model: TemplateModel,
            actionAfterEdit: (TemplateModel) -> Unit
    ): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = {
                model.apply {
                    dataManager.templateBox.apply {
                        this[model.id].let {
                            it.iconResId = iconResId
                            it.key = key
                            put(it)
                        }
                    }
                }
            },
            onSuccess = { actionAfterEdit(it) }
    )

}