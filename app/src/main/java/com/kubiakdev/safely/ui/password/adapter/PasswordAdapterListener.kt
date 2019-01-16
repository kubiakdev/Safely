package com.kubiakdev.safely.ui.password.adapter

import androidx.annotation.StringRes
import com.kubiakdev.safely.base.adapter.AdapterListener

interface PasswordAdapterListener : AdapterListener {

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)

    fun setTitleText(text: String)

    fun showSnackbar(@StringRes stringResId: Int)

}