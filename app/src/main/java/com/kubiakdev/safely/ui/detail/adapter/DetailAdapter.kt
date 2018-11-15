package com.kubiakdev.safely.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.base.adapter.BaseAdapterListener
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import com.kubiakdev.safely.data.model.DetailModel
import kotlinx.android.synthetic.main.item_detail_new.view.*

class DetailAdapter(
        override var list: MutableList<DetailModel>,
        override var dragListener: OnStartDragListener,
        var isInEditMode: Boolean
) : BaseAdapter<DetailModel, DetailAdapter.DetailHolder, AdapterListener>(list, dragListener) {

    override lateinit var adapterListener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder =
            DetailHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_detail_new, parent, false),
                    listener = adapterListener)

    override fun onItemDismiss(position: Int) {
        list.run {
            removeAt(position)
            if (isEmpty()) {
                adapterListener.switchOffDeleteMode()
            }
        }.also { notifyItemRemoved(position) }
    }

    inner class DetailHolder(
            override val view: View,
            override val listener: BaseAdapterListener
    ) : BaseAdapter.BaseHolder<DetailModel>(view, adapterListener) {

        override fun bindHolder(model: DetailModel, dragListener: OnStartDragListener) {
            view.run {
                iv_detail_new.run {
                    setImageResource(model.iconResId)
                    contentDescription = model.iconResId.toString()
                }
                tv_detail_new.run {
                    text = model.key
                }
            }
            view.setOnClickListener {
                val currentModel = DetailModel(
                        view.iv_detail_new.contentDescription.toString().toInt(),
                        view.tv_detail_new.text.toString()
                )
                if (isInEditMode) {
                    adapterListener.onEditTemplate(currentModel, adapterPosition)
                } else {
                    adapterListener.addDetail(currentModel)
                }
            }
        }
    }
}