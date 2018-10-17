package com.kubiakdev.safely.ui.main.fragment.password.adapter

import androidx.annotation.StringRes
import com.kubiakdev.safely.mvp.adapter.BaseAdapterListener

interface AdapterListener : BaseAdapterListener {

    fun showSnackBar(@StringRes stringRes: Int)

    fun switchOffDeleteMode()
}