package com.kubiakdev.safely.ui.main.adapter

import com.kubiakdev.safely.base.adapter.BaseAdapterListener

interface AdapterListener : BaseAdapterListener {

    fun showOnDeleteDialog(position: Int)
}