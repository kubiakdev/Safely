package com.kubiakdev.safely.ui.icon

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.util.livedata.ResettableMutableLiveData
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import javax.inject.Inject

class IconViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(coroutineContextProvider) {

    var iconResId = ResettableMutableLiveData(-1)
}