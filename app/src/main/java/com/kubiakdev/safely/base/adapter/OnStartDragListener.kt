package com.kubiakdev.safely.base.adapter

import androidx.recyclerview.widget.RecyclerView

interface OnStartDragListener {

    fun onDragStarted(viewHolder: RecyclerView.ViewHolder)

}