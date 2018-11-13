package com.kubiakdev.safely.ui.main.fragment.main

import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import javax.inject.Inject

class MainViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel(coroutineContextProvider)