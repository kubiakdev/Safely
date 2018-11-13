package com.kubiakdev.safely.ui.main.fragment.template

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import javax.inject.Inject

class TemplateViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private val dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    fun editTemplateAndBack(
            model: TemplateModel,
            index: Int,
            actionAfterEdit: (MutableList<DetailEntity>) -> Boolean
    ): Job = launchCatching(
            action = {
                dataManager.allDetailEntities.apply {
                    this[index].run {
                        iconResId = model.iconResId
                        key = model.key
                    }
                    dataManager.allDetailEntities = this
                }.toMutableList()
            },
            onSuccess = { actionAfterEdit.invoke(it) }
    )
}