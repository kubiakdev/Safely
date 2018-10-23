package com.kubiakdev.safely.ui.main.fragment.detail

import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.base.BaseView

interface DetailView : BaseView {

    fun updateAdapterList(list: List<DetailModel>)
}