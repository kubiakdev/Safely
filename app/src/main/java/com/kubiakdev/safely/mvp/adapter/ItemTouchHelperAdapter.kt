package com.kubiakdev.safely.mvp.adapter

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean

    fun onItemDismiss(position: Int)
}