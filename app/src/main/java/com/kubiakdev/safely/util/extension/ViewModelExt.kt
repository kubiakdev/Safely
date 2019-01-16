package com.kubiakdev.safely.util.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

inline fun <reified T : ViewModel> Fragment.getViewModel(
        viewModelFactory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, viewModelFactory)[T::class.java]

inline fun <reified T : ViewModel> Fragment.getSharedViewModel(
        viewModelFactory: ViewModelProvider.Factory
): T = ViewModelProviders.of(requireActivity(), viewModelFactory)[T::class.java]

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

inline fun <reified T : ViewModel> Fragment.getSharedViewModel(
        viewModelFactory: ViewModelProvider.Factory,
        body: T.() -> Unit = {}
): T = ViewModelProviders.of(requireActivity(), viewModelFactory)[T::class.java].also { body(it) }


