package com.kubiakdev.safely.ui.detail

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.entityToModel
import com.kubiakdev.safely.data.mapper.modelToEntity
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.livedata.ResettableMutableLiveData
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import javax.inject.Inject

class DetailViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private var dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    var isInDeleteMode = MutableLiveData<Boolean>()
    var isInEditMode = MutableLiveData<Boolean>()

    var newDetailIconResId = ResettableMutableLiveData(-1)
    var newDetailKey = ResettableMutableLiveData("")

    var editedDetailIndex = ResettableMutableLiveData(-1)
    var editedDetailIconResId = ResettableMutableLiveData(-1)
    var editedDetailKey = ResettableMutableLiveData("")

    init {
        isInDeleteMode.postValue(false)
        isInEditMode.postValue(false)
    }

    fun updateDatabase(data: List<DetailModel>): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = {
                dataManager.allDetailEntities = data.map { modelToEntity(it) }.toMutableList()
            }
    )

    fun getData(response: (List<DetailModel>) -> Unit): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = {
                dataManager.allDetailEntities.map { entityToModel(it) }
            },
            onSuccess = {
                if (it.isNotEmpty()) {
                    response(it)
                } else {
                    Const.DEFAULT_TEMPLATE_LIST.run {
                        updateDatabase(this)
                        response(this)
                    }
                }
            }
    )
}