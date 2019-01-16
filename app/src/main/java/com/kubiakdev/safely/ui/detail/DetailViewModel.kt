package com.kubiakdev.safely.ui.detail

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.entityToModel
import com.kubiakdev.safely.data.mapper.modelToEntity
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import javax.inject.Inject

class DetailViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private var dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    fun getData(response: (List<TemplateModel>) -> Unit): Job = launchCatching(
            action = {
                dataManager.templateBox.all.map {
                    entityToModel(it)
                }
            },
            onSuccess = {
                if (it.isNotEmpty()) {
                    response(it)
                } else {
                    Const.DEFAULT_TEMPLATE_LIST.apply {
                        addAllAndChangeIds(this) { list ->
                            response(list)
                        }
                    }
                }
            }
    )

    fun remove(id: Long, doAfterRemove: () -> Unit): Job = launchCatching(
            action = { dataManager.templateBox.remove(id) },
            onSuccess = { doAfterRemove() }
    )

    private fun addAllAndChangeIds(
            list: List<TemplateModel>,
            doAfterAdd: (List<TemplateModel>) -> Unit
    ): Job = launchCatching(
            action = {
                list.apply {
                    dataManager.templateBox.apply {
                        put(map { modelToEntity(it) })
                        all.forEachIndexed { i, entity ->
                            get(i).id = entity.id
                        }
                    }
                }
            },
            onSuccess = { doAfterAdd(it) }
    )

    fun switch(fromId: Long, toId: Long, doAfterSwitch: () -> Unit): Job = launchCatching(
            action = {
                dataManager.templateBox.apply {
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