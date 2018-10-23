package com.kubiakdev.safely.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.data.model.Model
import java.util.*

abstract class BaseAdapter<M : Model, H : BaseAdapter.BaseHolder<M>, I : BaseAdapterListener>(
        open var list: MutableList<M>,
        protected open val dragListener: OnStartDragListener
) : RecyclerView.Adapter<H>(), ItemTouchHelperAdapter {

    open lateinit var adapterListener: I

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H

    override fun onBindViewHolder(holder: H, position: Int) {
        holder.bindHolder(list[position], dragListener)
    }

    override fun getItemCount(): Int = list.size

    override fun onItemDismiss(position: Int) {}

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    abstract class BaseHolder<M>(
            protected open val view: View,
            protected open val listener: BaseAdapterListener
    ) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {

        override fun onItemSelected() {}

        override fun onItemClear() {}

        abstract fun bindHolder(model: M, dragListener: OnStartDragListener)
    }
}