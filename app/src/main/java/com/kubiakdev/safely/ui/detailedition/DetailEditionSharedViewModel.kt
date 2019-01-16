package com.kubiakdev.safely.ui.detailedition

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseSharedViewModel
import com.kubiakdev.safely.data.model.TemplateModel
import javax.inject.Inject

class DetailEditionSharedViewModel @Inject constructor() : BaseSharedViewModel() {

    var newModel = MutableLiveData<TemplateModel>()
    var editedModel = MutableLiveData<TemplateModel>()

}