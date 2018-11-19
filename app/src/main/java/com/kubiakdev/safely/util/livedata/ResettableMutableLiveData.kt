package com.kubiakdev.safely.util.livedata

import androidx.lifecycle.MutableLiveData

class ResettableMutableLiveData<T> constructor(private val defValue: T) : MutableLiveData<T>() {

    init {
        value = defValue
    }

    fun reset() {
        value = defValue
    }

    fun isEqualDefValue() = value == defValue

    fun isNotEqualDefValue() = value != defValue
}