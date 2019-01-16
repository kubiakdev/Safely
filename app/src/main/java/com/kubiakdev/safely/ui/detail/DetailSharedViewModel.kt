package com.kubiakdev.safely.ui.detail

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseSharedViewModel
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.data.model.TemplateModel
import javax.inject.Inject

class DetailSharedViewModel @Inject constructor() : BaseSharedViewModel() {

    var isInDeleteMode = MutableLiveData<Boolean>()
    var isInEditMode = MutableLiveData<Boolean>()
    var detailModelToEdit = MutableLiveData<TemplateModel?>()
    var newDetailModel = MutableLiveData<DetailModel>()

    init {
        isInDeleteMode.postValue(false)
        isInEditMode.postValue(false)
    }

}