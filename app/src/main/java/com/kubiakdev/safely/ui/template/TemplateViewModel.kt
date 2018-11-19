package com.kubiakdev.safely.ui.template

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.util.livedata.ResettableMutableLiveData
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import javax.inject.Inject

class TemplateViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private val dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    var newTemplateIconResId = ResettableMutableLiveData(-1)
    var newTemplateKey = ResettableMutableLiveData("")

    fun addTemplateAndPopBackStack(
            model: TemplateModel,
            actionAfterEdit: (MutableList<DetailEntity>) -> Boolean
    ): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = {
                val temp = dataManager.allDetailEntities
                temp.add(DetailEntity(model.iconResId, model.key))
                dataManager.allDetailEntities = temp
                temp.toMutableList()

            },
            onSuccess = { actionAfterEdit(it) }
    )

    fun editTemplateAndPopBackStack(
            model: TemplateModel,
            index: Int,
            actionAfterEdit: (MutableList<DetailEntity>) -> Boolean
    ): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = {
                dataManager.allDetailEntities.apply {
                    this[index].run {
                        iconResId = model.iconResId
                        key = model.key
                    }
                    dataManager.allDetailEntities = this
                }.toMutableList()
            },
            onSuccess = { actionAfterEdit(it) }
    )
}