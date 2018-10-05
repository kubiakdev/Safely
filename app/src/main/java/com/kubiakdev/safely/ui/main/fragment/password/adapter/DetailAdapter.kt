package com.kubiakdev.safely.ui.main.fragment.password.adapter

import android.content.ClipData
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.R
import kotlinx.android.synthetic.main.item_detail.view.*
import android.content.ClipboardManager
import android.content.Context
import android.text.Editable
import android.widget.ImageButton
import com.google.android.material.snackbar.Snackbar
import com.kubiakdev.safely.data.model.DetailModel


class DetailAdapter(
        var list: MutableList<DetailModel>
) : RecyclerView.Adapter<DetailAdapter.PasswordDetailHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordDetailHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail, parent, false)
        return PasswordDetailHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordDetailHolder, position: Int) {
        holder.bindHolder(list[position])
    }

    override fun getItemCount(): Int = list.size

    class PasswordDetailHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        fun bindHolder(item: DetailModel) {
            v.et_key.text = Editable.Factory.getInstance().newEditable(
                    v.context.getString(item.keyResId)
            )
            v.et_value.text = Editable.Factory.getInstance().newEditable(item.value)
            v.iv_icon.setImageResource(item.iconResId)
            setInputType(v.et_value, item.isShown ?: true)
            setShowAndHideIcon(v.ib_show, item.isShown ?: true)
            v.ib_copy.setOnClickListener {
                copyToClipboard(v.et_value)
                showSnackBar("Value copied")
            }
            v.ib_show.setOnClickListener {
                showOrHideValue(v.et_value, v.ib_show)
            item.isShown = !(item.isShown?:false)
            }
        }

        private fun setInputType(et: AppCompatEditText, isShown: Boolean) {
            if (isShown) {
                et.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                et.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        private fun copyToClipboard(et: AppCompatEditText) {
            val clipboard =
                    (et.context.getSystemService(Context.CLIPBOARD_SERVICE)) as ClipboardManager
            clipboard.primaryClip = ClipData.newPlainText("text", et.text.toString())
        }

        private fun showOrHideValue(et: AppCompatEditText, ib: ImageButton) {
            if (et.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                et.inputType = InputType.TYPE_CLASS_TEXT
                setShowAndHideIcon(ib, false)
            } else {
                et.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                setShowAndHideIcon(ib, true)
            }
        }

        private fun setShowAndHideIcon(ib: ImageButton, isShown: Boolean) {
            if (isShown) {
                ib.setImageResource(R.drawable.ic_visibility_off)
            } else {
                ib.setImageResource(R.drawable.ic_visibility)
            }
        }

        private fun showSnackBar(s: String) {
            Snackbar.make(v, s, Snackbar.LENGTH_SHORT).show()
        }
    }
}