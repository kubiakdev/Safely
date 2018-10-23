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
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import kotlinx.android.synthetic.main.item_detail.view.*
import java.util.*

class PasswordAdapter(
        override var list: MutableList<DetailModel>,
        override val dragListener: OnStartDragListener
) : BaseAdapter<DetailModel, PasswordAdapter.PasswordDetailHolder, AdapterListener>
(list, dragListener) {

    override lateinit var adapterListener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordDetailHolder =
            PasswordDetailHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_detail, parent, false),
                    listener = adapterListener)

    override fun onItemDismiss(position: Int) {
        list.run {
            removeAt(position)
            if (isEmpty()) {
                adapterListener.switchOffDeleteMode()
            }
        }.also { notifyItemRemoved(position) }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    class PasswordDetailHolder(
            override val view: View,
            override val listener: AdapterListener
    ) : BaseAdapter.BaseHolder<DetailModel>(view, listener) {

        override fun onItemSelected() {}

        override fun bindHolder(model: DetailModel, dragListener: OnStartDragListener) {
            view.cl_detail_item.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
            view.et_detail_item_key.text = Editable.Factory.getInstance().newEditable(model.key)
            view.et_detail_item_value.text = Editable.Factory.getInstance().newEditable(model.value)
            view.iv_detail_item_icon.setImageResource(model.iconResId)
            view.et_detail_item_value.requestFocus()
            view.et_detail_item_value.performClick()
            setInputType(view.et_detail_item_value, model.isShown)
            setShowAndHideIcon(view.ib_detail_item_show, model.isShown)
            view.ib_detail_item_copy.setOnClickListener {
                copyToClipboard(view.et_detail_item_value)
                listener.showSnackBar(R.string.app_name)
            }
            view.ib_detail_item_show.setOnClickListener {
                showOrHideValue(view.et_detail_item_value, view.ib_detail_item_show)
                model.isShown = !(model.isShown)
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