package com.kubiakdev.safely.ui.main.fragment.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.base.adapter.BaseAdapterListener
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import kotlinx.android.synthetic.main.item_detail_new.view.*

class DetailAdapter(
        override var list: MutableList<DetailModel>,
        override var dragListener: OnStartDragListener
) : BaseAdapter<DetailModel, DetailAdapter.DetailHolder, AdapterListener>(list, dragListener) {

    override lateinit var adapterListener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder =
            DetailHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_detail_new, parent, false),
                    listener = adapterListener)

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
                adapterListener.addDetail(
                        DetailModel(
                                view.iv_detail_new.contentDescription.toString().toInt(),
                                view.tv_detail_new.text.toString()
                        )
                )
            }
        }
    }
}