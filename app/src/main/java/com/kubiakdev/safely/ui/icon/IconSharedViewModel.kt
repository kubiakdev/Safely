package com.kubiakdev.safely.ui.icon

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseSharedViewModel
import javax.inject.Inject

class IconSharedViewModel @Inject constructor() : BaseSharedViewModel() {

    var selectedIconResId = MutableLiveData<Int>()

}