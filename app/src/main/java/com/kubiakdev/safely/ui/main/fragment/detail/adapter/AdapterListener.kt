package com.kubiakdev.safely.ui.main.fragment.detail.adapter

import com.kubiakdev.safely.base.adapter.BaseAdapterListener
import com.kubiakdev.safely.data.model.DetailModel

interface AdapterListener : BaseAdapterListener {

    val isInEditMode: Boolean?

    fun addDetail(model: DetailModel)

    fun addTemplate(model: DetailModel)

    fun switchOffDeleteMode()

    fun switchOffEditMode()

    fun onEditTemplate(currentModel: DetailModel, index: Int)
}