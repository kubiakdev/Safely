package com.kubiakdev.safely.ui.main.fragment.password.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.mvp.adapter.ItemTouchHelperAdapter
import com.kubiakdev.safely.mvp.adapter.ItemTouchHelperViewHolder
import com.kubiakdev.safely.mvp.adapter.OnStartDragListener
import com.kubiakdev.safely.util.KeyboardUtil
import kotlinx.android.synthetic.main.item_detail.view.*
import java.util.*


class PasswordAdapter(
        var list: MutableList<DetailModel>,
        private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<PasswordAdapter.PasswordDetailHolder>(), ItemTouchHelperAdapter {

    lateinit var adapterListener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordDetailHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail, parent, false)
        return PasswordDetailHolder(view, adapterListener)
    }

    override fun onBindViewHolder(holder: PasswordDetailHolder, position: Int) {
        holder.bindHolder(list[position], dragListener)
    }

    override fun getItemCount(): Int = list.size

    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        if (list.isEmpty()) {
            adapterListener.switchOffDeleteMode()
        }
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    class PasswordDetailHolder(
            private val v: View,
            private val listener: AdapterListener
    ) : RecyclerView.ViewHolder(v), ItemTouchHelperViewHolder {

        override fun onItemSelected() {
            v.et_detail_item_value.requestFocus()
            KeyboardUtil.showKeyboard(v)
        }

        override fun onItemClear() {}

        fun bindHolder(item: DetailModel, dragListener: OnStartDragListener) {
            v.cl_detail_item.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
            v.et_detail_item_key.text = Editable.Factory.getInstance().newEditable(item.key)
            v.et_detail_item_value.text = Editable.Factory.getInstance().newEditable(item.value)
            v.iv_detail_item_icon.setImageResource(item.iconResId)
            v.et_detail_item_value.requestFocus()
            KeyboardUtil.showKeyboard(v)
            setInputType(v.et_detail_item_value, item.isShown)
            setShowAndHideIcon(v.ib_detail_item_show, item.isShown)
            v.ib_detail_item_copy.setOnClickListener {
                copyToClipboard(v.et_detail_item_value)
                listener.showSnackBar(R.string.app_name)
            }
            v.ib_detail_item_show.setOnClickListener {
                showOrHideValue(v.et_detail_item_value, v.ib_detail_item_show)
                item.isShown = !(item.isShown)
            }
        }

        private fun setInputType(et: AppCompatEditText, isShown: Boolean) {
            et.inputType.run {
                if (isShown) {
                    InputType.TYPE_CLASS_TEXT
                } else {
                    InputType.TYPE_NUMBER_VARIATION_PASSWORD
                }
            }
        }

        private fun copyToClipboard(et: AppCompatEditText) {
            ((et.context.getSystemService(Context.CLIPBOARD_SERVICE)) as ClipboardManager).run {
                primaryClip = ClipData.newPlainText("text", et.text.toString())
            }
        }

        private fun showOrHideValue(et: AppCompatEditText, ib: ImageButton) {
            et.inputType.run {
                if (this == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    setShowAndHideIcon(ib, true)
                    InputType.TYPE_CLASS_TEXT
                } else {
                    setShowAndHideIcon(ib, false)
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
            }
        }

        private fun setShowAndHideIcon(ib: ImageButton, isShown: Boolean) {
            ib.run {
                if (isShown) {
                    setImageResource(R.drawable.ic_visibility_off)
                } else {
                    setImageResource(R.drawable.ic_visibility)
                }
            }
        }
    }
}