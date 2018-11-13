package com.kubiakdev.safely.ui.main.fragment.icon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.base.adapter.BaseAdapterListener
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import com.kubiakdev.safely.data.model.IconModel
import com.kubiakdev.safely.util.extension.setSameOnClickListenerFor
import kotlinx.android.synthetic.main.item_icon.view.*

class IconAdapter(
        override var list: MutableList<IconModel>,
        override var dragListener: OnStartDragListener
) : BaseAdapter<IconModel, IconAdapter.IconHolder, AdapterListener>(list, dragListener) {

    override lateinit var adapterListener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconHolder =
            IconHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_icon, parent, false),
                    listener = adapterListener)

    inner class IconHolder(
            override val view: View,
            override val listener: BaseAdapterListener
    ) : BaseAdapter.BaseHolder<IconModel>(view, adapterListener) {

        override fun bindHolder(model: IconModel, dragListener: OnStartDragListener) {
            view.run {
                ib_icon.run {
                    setImageResource(model.iconResId)
                    contentDescription = model.iconResId.toString()
                }
                setSameOnClickListenerFor(
                        { adapterListener.onIconClicked(model.iconResId) },
                        mcv_icon, cl_icon, ib_icon
                )
            }
        }
    }
}