package com.kubiakdev.safely.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView

fun setSameOnClickListenerFor(action: () -> Unit, vararg views: View) {
    views.forEach { it.setOnClickListener { action.invoke() } }
}

fun toEditable(value: String): Editable = Editable.Factory.getInstance().newEditable(value)

fun TextView.addAfterTextChangedListener(afterTextChanged: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
        ) {
        }

        override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
        ) {
        }
    })
}
