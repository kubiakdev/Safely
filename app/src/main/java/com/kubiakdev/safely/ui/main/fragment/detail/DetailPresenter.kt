package com.kubiakdev.safely.ui.main.fragment.detail

import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.mapEntityToModel
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.mvp.BasePresenter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter @Inject constructor() : BasePresenter<DetailView>() {

    @Inject
    lateinit var dataManager: DataManager

    val detailList: Single<List<DetailModel>> by lazy {
        dataManager.getAllDetailEntities(Schedulers.computation())
                .flatMapIterable { it }
                .map { mapEntityToModel(it) }
                .toList()
    }
}