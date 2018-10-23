package com.kubiakdev.safely.util

import androidx.navigation.NavController
import com.kubiakdev.safely.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

sealed class DefaultArgumentsDelegate<T>(protected val key: kotlin.String) :
        ReadWriteProperty<NavController, T> {

    class Int(key: kotlin.String) : DefaultArgumentsDelegate<kotlin.Int>(key) {

        override fun getValue(thisRef: NavController, property: KProperty<*>) =
                onGetValue(thisRef, false)

        override fun setValue(
                thisRef: NavController,
                property: KProperty<*>,
                value: kotlin.Int
        ) =
                thisRef.graph.defaultArguments.putInt(key, value)
    }

    class DrawableResInt(key: kotlin.String) : DefaultArgumentsDelegate<kotlin.Int>(key) {

        override fun getValue(thisRef: NavController, property: KProperty<*>) =
                onGetValue(thisRef, true)

        override fun setValue(
                thisRef: NavController,
                property: KProperty<*>,
                value: kotlin.Int
        ) =
                thisRef.graph.defaultArguments.putInt(key, value)
    }

    class String(key: kotlin.String) : DefaultArgumentsDelegate<kotlin.String>(key) {

        override fun getValue(thisRef: NavController, property: KProperty<*>) =
                thisRef.graph.defaultArguments.run {
                    if (containsKey(key)) {
                        getString(key).also {
                            remove(key)
                        }
                    } else {
                        ""
                    } ?: ""
                }

        override fun setValue(
                thisRef: NavController,
                property: KProperty<*>,
                value: kotlin.String
        ) =
                thisRef.graph.defaultArguments.putString(key, value)
    }

    protected fun onGetValue(thisRef: NavController, isDrawableResInt: Boolean) =
            thisRef.graph.defaultArguments.run {
                if (containsKey(key)) {
                    getInt(key).also {
                        remove(key)
                    }
                } else {
                    if (isDrawableResInt) {
                        R.drawable.ic_image
                    } else {
                        -1
                    }
                }
            }

}