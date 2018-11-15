package com.kubiakdev.safely.ui.password.adapter

import androidx.annotation.StringRes
import com.kubiakdev.safely.base.adapter.BaseAdapterListener

interface AdapterListener : BaseAdapterListener {

    fun showSnackBar(@StringRes stringRes: Int)

    fun switchOffDeleteMode()

    fun setTitleText(text: String)
}