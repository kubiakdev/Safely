package com.kubiakdev.safely.mvp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.data.model.Model
import java.util.*

abstract class BaseAdapter<T : Model>(
        open var list: MutableList<T>,
        protected open val dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseAdapter.BaseHolder<T>>(), ItemTouchHelperAdapter {

    protected abstract val layoutResId : Int

    open lateinit var adapterListener: BaseAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> =
            BaseHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(layoutResId, parent, false),
                    listener = adapterListener
            )

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.bindHolder(list[position], dragListener)
    }

    override fun getItemCount(): Int = list.size

    abstract override fun onItemDismiss(position: Int)

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    open class BaseHolder<T>(
            protected open val view: View,
            protected open val listener: BaseAdapterListener
    ) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {

        override fun onItemSelected() {}

        override fun onItemClear() {}

        open fun bindHolder(model: T, dragListener: OnStartDragListener) {}
    }
}