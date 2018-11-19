package com.kubiakdev.safely.util.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

inline fun <reified T : ViewModel> Fragment.getViewModel(
        viewModelFactory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, viewModelFactory)[T::class.java]

inline fun <reified T : ViewModel> withViewModel(
        viewModel: T, body: T.() -> Unit
): T = viewModel.apply { body() }

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

