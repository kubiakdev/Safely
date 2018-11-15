package com.kubiakdev.safely.ui.main.fragment.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import com.kubiakdev.safely.data.model.PasswordModel
import kotlinx.android.synthetic.main.item_password.view.*
import java.util.*

class MainAdapter(
        override var list: MutableList<PasswordModel>,
        override val dragListener: OnStartDragListener
) : BaseAdapter<PasswordModel, MainAdapter.MainHolder, AdapterListener>
(list, dragListener) {

    override lateinit var adapterListener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
            MainHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_password, parent, false),
                    listener = adapterListener)

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition == 0 || toPosition == 0) return false
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        adapterListener.showOnDeleteDialog(position)
    }


    inner class MainHolder(
            override val view: View,
            override val listener: AdapterListener
    ) : BaseAdapter.BaseHolder<PasswordModel>(view, listener) {

        override fun onItemSelected() {}

        override fun bindHolder(model: PasswordModel, dragListener: OnStartDragListener) {
            view.run {
                mcv_password_item.setCardBackgroundColor(model.cardColor.toInt())
                tv_password_item.text = model.title
                setFavouriteDrawable(ib_password_item, model.isFavourite)

                ib_password_item.run {
                    setFavouriteDrawable(this, model.isFavourite)
                    setOnClickListener {
                        model.isFavourite = !model.isFavourite.also { isFavourite ->
                            setFavouriteDrawable(this, isFavourite)
                            list[adapterPosition].isFavourite = isFavourite
                        }
                    }
                }
            }
        }
    }

    private fun setFavouriteDrawable(ib: AppCompatImageButton, isFavourite: Boolean) {
        ib.setImageResource(
                if (isFavourite) {
                    R.drawable.ic_star
                } else {
                    R.drawable.ic_star_border
                }
        )
    }
}