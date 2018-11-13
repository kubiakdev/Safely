package com.kubiakdev.safely.ui.main.fragment.password.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.util.extension.addAfterTextChangedListener
import com.kubiakdev.safely.util.extension.toEditable
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

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition == 0 || toPosition == 0) return false
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        list.run {
            removeAt(position)
            if (isEmpty()) {
                adapterListener.switchOffDeleteMode()
            }
        }.also { notifyItemRemoved(position) }
    }


    inner class PasswordDetailHolder(
            override val view: View,
            override val listener: AdapterListener
    ) : BaseAdapter.BaseHolder<DetailModel>(view, listener) {

        override fun onItemSelected() {}

        override fun bindHolder(model: DetailModel, dragListener: OnStartDragListener) {
            view.run {
                et_detail_item_key.text = toEditable(model.key)
                et_detail_item_value.text = toEditable(model.value)
                iv_detail_item_icon.setImageResource(model.iconResId)

                if (adapterPosition == 0) {
                    et_detail_item_key.run {
                        addAfterTextChangedListener {
                            val title = resources.getString(R.string.all_new_title)
                            this.text = toEditable(title)
                            list[adapterPosition].key = title
                        }
                    }

                    et_detail_item_value.run {
                        addAfterTextChangedListener {
                            adapterListener.setTitleText(
                                    if (text.isNullOrEmpty()) {
                                        resources.getString(R.string.password_title_new)
                                    } else {
                                        it.toString()
                                    }
                            )
                            list[adapterPosition].value = et_detail_item_value.text.toString()
                        }
                    }
                } else {
                    et_detail_item_key.run {
                        addAfterTextChangedListener {
                            list[adapterPosition].key = it.toString()
                        }
                    }

                    et_detail_item_value.run {
                        addAfterTextChangedListener {
                            list[adapterPosition].value = it.toString()
                        }
                    }
                }

                cl_detail_item.setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this@PasswordDetailHolder)
                    }
                    false
                }

                et_detail_item_value.run {
                    text = toEditable(model.value)
                    requestFocus()
                    performClick()
                }

                ib_detail_item_copy.setOnClickListener {
                    copyToClipboard(view.et_detail_item_value)
                    listener.showSnackBar(R.string.all_value_copied)
                }

                setInputType(model.isShown)
                ib_detail_item_show.setOnClickListener {
                    if (model.isShown) {
                        model.isShown = false
                        list[adapterPosition].isShown = false
                        setInputType(false)
                    } else {
                        model.isShown = true
                        list[adapterPosition].isShown = true
                        setInputType(true)
                    }
                }
            }
        }

        private fun copyToClipboard(et: AppCompatEditText) {
            ((et.context.getSystemService(Context.CLIPBOARD_SERVICE)) as ClipboardManager).run {
                primaryClip = ClipData.newPlainText("text", et.text.toString())
            }
        }

        private fun setInputType(shouldBeShown: Boolean) {
            view.et_detail_item_value.run {
                inputType = if (shouldBeShown) {
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD.also {
                                view.ib_detail_item_show
                                        .setImageResource(R.drawable.ic_visibility_off)
                            }
                } else {
                    (InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_FLAG_MULTI_LINE).also {
                        view.ib_detail_item_show.setImageResource(R.drawable.ic_visibility_on)
                    }
                }
                setSelection(text?.length ?: 0)
                typeface = Typeface.create(MainValues.FONT_FAMILY_NAME, Typeface.NORMAL)
            }
        }
    }
}