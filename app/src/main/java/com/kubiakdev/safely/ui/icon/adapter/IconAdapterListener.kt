package com.kubiakdev.safely.ui.icon.adapter

import com.kubiakdev.safely.base.adapter.AdapterListener

interface IconAdapterListener : AdapterListener {

    fun onIconClicked(iconResId: Int)

}