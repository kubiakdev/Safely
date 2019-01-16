package com.kubiakdev.safely.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.ui.main.adapter.item.DetailKeyItem
import com.kubiakdev.safely.ui.main.adapter.item.PasswordItem
import kotlinx.android.synthetic.main.item_detail_key.view.*
import kotlinx.android.synthetic.main.item_password.view.*

class MainAdapter(
        override var list: MutableList<PasswordItem>,
        override var listener: MainAdapterListener
) : BaseAdapter<PasswordItem, MainAdapter.MainViewHolder, MainAdapterListener>(list, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
            MainViewHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_password, parent, false),
                    listener = listener
            )

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listener.onItemMove(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        listener.onItemDismiss(position)
    }

    class MainViewHolder(
            override val view: View,
            override var listener: MainAdapterListener
    ) : BaseAdapter.BaseViewHolder<PasswordItem, MainAdapterListener>(view, listener) {

        override fun bindHolder(item: PasswordItem) {
            view.apply {
                tvPasswordItem.text = item.title

                ibPasswordItem.apply {
                    setFavouriteDrawable(this, item.isFavourite)
                    setOnClickListener {
                        item.isFavourite = !item.isFavourite
                        setFavouriteDrawable(this, item.isFavourite)
                    }
                }

                rvPasswordItem.apply {
                    layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, ORIENTATION)
                    this.adapter = DetailKeyAdapter(
                            list = item.detailList
                                    .filterIndexed { index, _ -> index != 0 }
                                    .map { DetailKeyItem(it.key) }
                                    .toMutableList(),
                            listener = listener,
                            parentAdapterPosition = adapterPosition
                    ).also { it.notifyDataSetChanged() }
                }
            }
        }

        override fun onClick(v: View?) {
            listener.onItemClicked(adapterPosition)
        }

        private fun setFavouriteDrawable(ib: AppCompatImageButton, isFavourite: Boolean) {
            ib.setImageResource(
                    if (isFavourite) R.drawable.ic_star else R.drawable.ic_star_border
            )
        }
    }

    class DetailKeyAdapter(
            override var list: MutableList<DetailKeyItem>,
            override var listener: MainAdapterListener,
            private val parentAdapterPosition: Int
    ) : BaseAdapter<DetailKeyItem, DetailKeyAdapter.DetailKeyViewHolder, MainAdapterListener>(
            list,
            listener
    ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailKeyViewHolder =
                DetailKeyViewHolder(
                        view = LayoutInflater.from(parent.context)
                                .inflate(R.layout.item_detail_key, parent, false),
                        listener = listener
                )

        inner class DetailKeyViewHolder(
                override var view: View,
                override var listener: MainAdapterListener
        ) : BaseViewHolder<DetailKeyItem, MainAdapterListener>(view, listener) {

            override fun bindHolder(item: DetailKeyItem) {
                view.tvDetailKeyItem.text = item.key
            }

            override fun onClick(v: View?) {
                listener.onItemClicked(parentAdapterPosition)
            }
        }
    }

    companion object {

        private const val SPAN_COUNT = 1
        private const val ORIENTATION = 1

    }

}