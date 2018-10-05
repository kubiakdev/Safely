package com.kubiakdev.safely.ui.main.fragment.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import kotlinx.android.synthetic.main.item_detail_new.view.*

class DetailAdapter(
        var list: MutableList<DetailModel>
) : RecyclerView.Adapter<DetailAdapter.NewDetailHolder>() {

    lateinit var listener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewDetailHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_new, parent, false)
        return NewDetailHolder(view)
    }

    override fun onBindViewHolder(holder: NewDetailHolder, position: Int) {
        holder.bindHolder(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class NewDetailHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        fun bindHolder(model: DetailModel) {
            v.run {
                iv_detail_new.run {
                    setImageResource(model.iconResId)
                    tag = model.iconResId.toString()
                }
                tv_detail_new.run {
                    text = context.getText(model.keyResId)
                    tag = model.keyResId
                }
            }
            v.setOnClickListener {
                listener.addDetail(
                        DetailModel(
                                v.iv_detail_new.tag.toString().toInt(),
                                v.tv_detail_new.tag.toString().toInt()
                        )
                )
            }
        }
    }
}