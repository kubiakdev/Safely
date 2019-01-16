package com.kubiakdev.safely.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.ui.detail.adapter.item.TemplateItem
import kotlinx.android.synthetic.main.item_detail_new.view.*

class DetailAdapter(
        override var list: MutableList<TemplateItem>,
        override var listener: DetailAdapterListener
) : BaseAdapter<TemplateItem, DetailAdapter.DetailViewHolder, DetailAdapterListener>(list, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder =
            DetailViewHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_detail_new, parent, false),
                    listener = listener
            )

    inner class DetailViewHolder(
            override var view: View,
            override var listener: DetailAdapterListener
    ) : BaseAdapter.BaseViewHolder<TemplateItem, DetailAdapterListener>(view, listener) {

        override fun bindHolder(item: TemplateItem) {
            view.apply {
                ivDetailNew.apply {
                    setImageResource(item.iconResId)
                    contentDescription = item.iconResId.toString()
                }

                tvDetailNew.apply {
                    text = item.key
                }
            }
        }

        override fun onClick(v: View?) {
            view.apply {
                listener.onItemSelected(
                        adapterPosition,
                        TemplateModel(
                                list[adapterPosition].id,
                                ivDetailNew.contentDescription.toString().toInt(),
                                tvDetailNew.text.toString()
                        )
                )
            }
        }

    }

}