package com.kubiakdev.safely.ui.main.fragment.detail.adapter

import com.kubiakdev.safely.data.model.DetailModel

interface AdapterListener{

    fun addDetail(model: DetailModel)

    fun addTemplate(model: DetailModel)
}