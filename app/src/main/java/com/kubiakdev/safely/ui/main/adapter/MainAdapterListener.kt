package com.kubiakdev.safely.ui.main.adapter

import com.kubiakdev.safely.base.adapter.AdapterListener

interface MainAdapterListener : AdapterListener {

    fun onItemClicked(position: Int)

    fun onItemDismiss(position: Int)

    fun onItemMove(fromPosition: Int, toPosition: Int)

}