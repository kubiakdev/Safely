package com.kubiakdev.safely.ui.main.fragment.detail

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.mapper.entityToModel
import com.kubiakdev.safely.data.mapper.modelToEntity
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.ui.main.MainValues
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

    init {
        isInDeleteMode.postValue(false)
        isInEditMode.postValue(false)
    }

    fun updateDatabase(data: List<DetailModel>): Job = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = { dataManager.allDetailEntities = data.map { modelToEntity(it) } }
    )

    fun getData(response: (List<DetailModel>) -> Unit): Job = launchCatching(
            action = { dataManager.allDetailEntities.map { entityToModel(it) } },
            onSuccess = {
                if (it.isNotEmpty()) {
                    response.invoke(it)
                } else {
                    MainValues.DEFAULT_TEMPLATE_LIST.run {
                        response.invoke(this)
                        updateDatabase(this)
                    }
                }
            },
            onFailure = { it.printStackTrace() }
    )
}

//    lateinit var dataManager: DataManager

//    fun getData() =
//            executeAsyncSeq(
//                    context = UI + parentJob,
//                    doOnError = { e ->
//                        ExceptionHandler(
//                                exception = e,
//                                onNetworkErrorException = { println(" dziala") }
//                        )
//                    },
//                    block = {
//                        dataManager.getAllDetailEntities().map { entityToModel(it) }.also {
//                            println("testt ${Thread.currentThread().name}")
//                        }
//                    },
//                    response = { list ->
//                        //                        view?.tryUpdateAdapterList(
////                                if (list.isEmpty()) {
////                                    MainValues.DEFAULT_TEMPLATE_LIST
////                                } else {
////                                    list
////                                }
////                        ).also { println("testt2 ${Thread.currentThread().name}") }
//                        throw NetworkErrorException()
//                    }
//            )

//    var modelList : List<DetailModel> = executeAsyncSeq(
////            context =  Contacts.Intents.UI + parentJob,
//            context = parentJob,
//            executors = *arrayOf(object : CoroutineExecutor<Any> {
//
//                override fun doOnBlock(): List<DetailModel> =
//                //                        dataManager.getAllDetailEntities().map { entityToModel(it) }
//                        listOf(DetailModel(2, "53"))
//
//                override fun doOnResponse(block: Any) {
//                    //                    view?.tryUpdateAdapterList(
//                    //                            if (block.isEmpty()) {
//                    //                                MainValues.DEFAULT_TEMPLATE_LIST
//                    //                            } else {
//                    //                                block
//                    //                            })
//                    //                    println("dupppaaa")
//                }
//            })
//    )