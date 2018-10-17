package com.kubiakdev.safely.ui.main.fragment.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.mvp.adapter.BaseAdapter
import com.kubiakdev.safely.mvp.adapter.BaseAdapterListener
import com.kubiakdev.safely.mvp.adapter.OnStartDragListener
import kotlinx.android.synthetic.main.item_detail_new.view.*

class DetailAdapter(
        override var list: MutableList<DetailModel>,
        override var dragListener: OnStartDragListener
) : BaseAdapter(list, dragListener) {

    override lateinit var adapterListener: BaseAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_new, parent, false)
        return DetailHolder(view)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindHolder(list[position], dragListener)
    }

    override fun onItemDismiss(position: Int) {}

    override fun getItemCount(): Int = list.size

    inner class DetailHolder(override val v: View) : BaseHolder(v, adapterListener) {

        override fun bindHolder(model: DetailModel, dragListener: OnStartDragListener) {
            v.run {
                iv_detail_new.run {
                    setImageResource(model.iconResId)
                    contentDescription = model.iconResId.toString()
                }
                tv_detail_new.run {
                    text = model.key
                }
            }
            v.setOnClickListener {
                (adapterListener as AdapterListener).addDetail(
                        DetailModel(
                                v.iv_detail_new.contentDescription.toString().toInt(),
                                v.tv_detail_new.text.toString()
                        )
                )
            }
        }

        override fun onItemClear() {}

        override fun onItemSelected() {}
    }
}