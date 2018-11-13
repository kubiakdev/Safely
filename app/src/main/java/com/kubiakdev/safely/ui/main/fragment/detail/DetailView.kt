package com.kubiakdev.safely.ui.main.fragment.detail

import com.kubiakdev.safely.base.BaseView
import com.kubiakdev.safely.data.model.DetailModel

interface DetailView : BaseView {

    fun tryUpdateAdapterList(list: List<DetailModel>?)
}