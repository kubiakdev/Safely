package com.kubiakdev.safely.mvp

interface FragmentListener {

    fun doOnMenuActionClick(action: String, value: Boolean = true)
}