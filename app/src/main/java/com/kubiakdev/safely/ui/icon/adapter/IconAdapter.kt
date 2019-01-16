package com.kubiakdev.safely.ui.icon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.ui.icon.adapter.item.IconItem
import kotlinx.android.synthetic.main.item_icon.view.*

class IconAdapter(
        override var list: MutableList<IconItem>,
        override var listener: IconAdapterListener
) : BaseAdapter<IconItem, IconAdapter.IconViewHolder, IconAdapterListener>(list, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder =
            IconViewHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_icon, parent, false),
                    listener = listener
            )

    class IconViewHolder(
            override val view: View,
            override var listener: IconAdapterListener
    ) : BaseAdapter.BaseViewHolder<IconItem, IconAdapterListener>(view, listener) {

        override fun bindHolder(item: IconItem) {
            view.apply {
                ibIcon.apply {
                    setImageResource(item.iconResId)
                    contentDescription = item.iconResId.toString()
                }

                setOnClickListener {
                    listener.onIconClicked(item.iconResId)
                }
            }
        }
    }

}