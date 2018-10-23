package com.kubiakdev.safely.base.adapter

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class SimpleItemTouchHelperCallback(
        var adapter: ItemTouchHelperAdapter,
        var isInDeleteMode: Boolean = false
) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean = true

    override fun isItemViewSwipeEnabled(): Boolean = true

    override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            return ItemTouchHelper.Callback.makeMovementFlags(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    , 0
            )
        } else {
            return if (isInDeleteMode) {
                ItemTouchHelper.Callback.makeMovementFlags(
                        0,
                        ItemTouchHelper.START or ItemTouchHelper.END
                )
            } else {
                ItemTouchHelper.Callback.makeMovementFlags(
                        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        0
                )
            }
        }
    }

    override fun onMove(
            recyclerView: RecyclerView,
            source: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
    ): Boolean {
        return if (source.itemViewType != target.itemViewType) {
            false
        } else {
            adapter.onItemMove(source.adapterPosition, target.adapterPosition)
            true
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemTouchHelperViewHolder) {
                (viewHolder as ItemTouchHelperViewHolder?)?.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is ItemTouchHelperViewHolder) {
            (viewHolder as ItemTouchHelperViewHolder).onItemClear()
        }
    }
}