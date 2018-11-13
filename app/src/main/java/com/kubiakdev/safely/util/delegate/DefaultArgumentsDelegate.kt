package com.kubiakdev.safely.util.delegate

import androidx.navigation.NavController
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

sealed class DefaultArgumentsDelegate<T>(
        protected val key: kotlin.String,
        protected val defaultIntValue: kotlin.Int = -1,
        protected val defaultStringValue: kotlin.String = ""
) : ReadWriteProperty<NavController, T> {

    class Int(
            key: kotlin.String,
            defaultIntValue: kotlin.Int = -1
    ) : DefaultArgumentsDelegate<kotlin.Int>(key, defaultIntValue = defaultIntValue) {

        override fun getValue(thisRef: NavController, property: KProperty<*>) =
                thisRef.graph.defaultArguments.getInt(key, defaultIntValue)

        override fun setValue(
                thisRef: NavController,
                property: KProperty<*>,
                value: kotlin.Int
        ) = thisRef.graph.defaultArguments.putInt(key, value)
    }

    class String(
            key: kotlin.String,
            defaultStringValue: kotlin.String = ""
    ) : DefaultArgumentsDelegate<kotlin.String>(key, defaultStringValue = defaultStringValue) {

        override fun getValue(thisRef: NavController, property: KProperty<*>) =
                thisRef.graph.defaultArguments
                        .getString(key, defaultStringValue) ?: defaultStringValue

        override fun setValue(
                thisRef: NavController,
                property: KProperty<*>,
                value: kotlin.String
        ) = thisRef.graph.defaultArguments.putString(key, value)
    }
}