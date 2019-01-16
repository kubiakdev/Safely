package com.kubiakdev.safely.ui.detail.adapter

import com.kubiakdev.safely.base.adapter.AdapterListener
import com.kubiakdev.safely.data.model.TemplateModel

interface DetailAdapterListener : AdapterListener {

    fun onItemSelected(position: Int, model: TemplateModel)

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)

}