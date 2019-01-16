package com.kubiakdev.safely.ui.password.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.BaseAdapter
import com.kubiakdev.safely.ui.password.adapter.item.DetailItem
import com.kubiakdev.safely.util.extension.addAfterTextChangedListener
import kotlinx.android.synthetic.main.item_detail.view.*

class PasswordAdapter(
        override var list: MutableList<DetailItem>,
        override var listener: PasswordAdapterListener,
        var isNewPassword: Boolean
) : BaseAdapter<DetailItem, PasswordAdapter.PasswordViewHolder, PasswordAdapterListener>(
        list, listener
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder =
            PasswordViewHolder(
                    view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_detail, parent, false),
                    listener = listener,
                    isNewPassword = isNewPassword
            )

    class PasswordViewHolder(
            override val view: View,
            override var listener: PasswordAdapterListener,
            private val isNewPassword: Boolean
    ) : BaseAdapter.BaseViewHolder<DetailItem, PasswordAdapterListener>(view, listener) {

        override fun bindHolder(item: DetailItem) {
            view.apply {
                ivDetailItemIcon.setImageResource(item.iconResId)

                ivDetailItemCopy.setOnClickListener {
                    copyToClipboard(tilDetailItem.editText) {
                        listener.showSnackbar(R.string.all_value_copied)
                    }
                }

                tilDetailItem.apply {
                    hint = item.key
                    editText?.apply {
                        setText(item.value)
                        requestFocus()
                        addAfterTextChangedListener {
                            item.value = it.toString()
                            if (adapterPosition == 0) {
                                listener.setTitleText(
                                        if (item.value.isEmpty()) {
                                            context.getString(R.string.password_title_default)
                                        } else {
                                            item.value
                                        }
                                )
                            }
                        }

                        if (adapterPosition == 0) {
                            listener.setTitleText(item.value)
                            inputType = InputType.TYPE_CLASS_TEXT
                        }
                    }

                    if (isNewPassword || adapterPosition == 0) {
                        isPasswordVisibilityToggleEnabled = false
                    }
                }
            }
        }

        private fun copyToClipboard(et: EditText?, actionAfterCopy: () -> Unit) {
            et?.apply {
                if (text?.isNotBlank() == true) {
                    ((context.getSystemService(Context.CLIPBOARD_SERVICE)) as ClipboardManager)
                            .primaryClip = ClipData.newPlainText(COPY_LABEL, text.toString())
                }
                actionAfterCopy()
            }
        }
    }

    companion object {

        private const val COPY_LABEL = "text"

    }

}