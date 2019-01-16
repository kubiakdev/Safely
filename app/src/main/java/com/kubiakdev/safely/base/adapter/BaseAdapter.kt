package com.kubiakdev.safely.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<I : ListItem, H : BaseAdapter.BaseViewHolder<I, L>, L : AdapterListener>(
        open var list: MutableList<I>,
        open var listener: L
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<I, L>>(), ItemTouchHelperAdapter {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H

    override fun onBindViewHolder(holder: BaseViewHolder<I, L>, position: Int) {
        holder.apply {
            bindHolder(list[position])
            itemView.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onItemDismiss(position: Int) {}

    override fun onItemMove(fromPosition: Int, toPosition: Int) {}

    abstract class BaseViewHolder<I : ListItem, L : AdapterListener>(
            protected open val view: View,
            protected open var listener: L
    ) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder, View.OnClickListener {

        override fun onItemSelected() {}

        override fun onItemClear() {}

        abstract fun bindHolder(item: I)

        override fun onClick(v: View?) {}

    }

}