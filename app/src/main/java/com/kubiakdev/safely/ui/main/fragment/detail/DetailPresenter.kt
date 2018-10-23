package com.kubiakdev.safely.ui.main.fragment.detail

import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.mapEntityToModel
import com.kubiakdev.safely.base.BasePresenter
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.util.asyncExecutor
import kotlinx.coroutines.experimental.android.UI
import javax.inject.Inject

class DetailPresenter @Inject constructor() : BasePresenter<DetailView>() {

    @Inject
    lateinit var dataManager: DataManager

    fun loadDataToAdapter() =
            asyncExecutor(
                    UI + parentJob,
                    { dataManager.getAllDetailEntities().map { mapEntityToModel(it) }.also {
                        println("duppp ${Thread.currentThread().name}")
                    } },
                    { list ->
                        view?.updateAdapterList(
                                if (list.isEmpty()) {
                                    MainValues.DEFAULT_TEMPLATE_LIST
                                } else {
                                    list
                                }
                        ).also {  println("duppp2 ${Thread.currentThread().name}") }
                    }
            )
}