package com.kubiakdev.safely.ui.main.fragment.detail.adapter

import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.base.adapter.BaseAdapterListener

interface AdapterListener : BaseAdapterListener {

    fun addDetail(model: DetailModel)

    fun addTemplate(model: DetailModel)
}