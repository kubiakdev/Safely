package com.kubiakdev.safely.ui.main.fragment.icon.adapter

import com.kubiakdev.safely.base.adapter.BaseAdapterListener

interface AdapterListener : BaseAdapterListener {

    fun onIconClicked(iconResId: Int)
}