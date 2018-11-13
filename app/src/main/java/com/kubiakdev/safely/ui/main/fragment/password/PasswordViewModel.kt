package com.kubiakdev.safely.ui.main.fragment.password

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import javax.inject.Inject

class PasswordViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(coroutineContextProvider) {

    var isInDeleteMode = MutableLiveData<Boolean>()

    init {
        isInDeleteMode.postValue(false)
    }
}