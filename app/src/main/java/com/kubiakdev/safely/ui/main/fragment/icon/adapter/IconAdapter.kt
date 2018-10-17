package com.kubiakdev.safely.ui.main.fragment.icon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.IconModel
import com.kubiakdev.safely.mvp.adapter.BaseAdapter
import com.kubiakdev.safely.mvp.adapter.BaseAdapterListener
import com.kubiakdev.safely.mvp.adapter.OnStartDragListener

class IconAdapter(
        override var list: MutableList<IconModel>,
        override var dragListener: OnStartDragListener
) : BaseAdapter<IconModel>(list, dragListener) {

    override val layoutResId: Int = R.layout.

    override lateinit var adapterListener: BaseAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_new, parent, false)
        return IconHolder(view)
    }

    override fun onBindViewHolder(holder: BaseHolder<IconModel>, position: Int) {
        holder.bindHolder(list[position], dragListener)
    }

    override fun onItemDismiss(position: Int) {}

    override fun getItemCount(): Int = list.size

    inner class IconHolder(override val view: View) : BaseHolder<IconModel>(view, adapterListener) {

        override fun bindHolder(model: IconModel, dragListener: OnStartDragListener) {
        }

        override fun onItemClear() {}

        override fun onItemSelected() {}
    }
}