package com.kubiakdev.safely.data.model

data class DetailModel(var iconResId: Int, var key: String) : Model {
    var id: Long? = 0

    var value: String = ""
    var isShown: Boolean = false

    constructor(
            iconResId: Int,
            key: String,
            value: String,
            isShown: Boolean
    ) : this(
            iconResId,
            key
    ) {
        this.iconResId = iconResId
        this.key = key
        this.value = value
        this.isShown = isShown
    }

    constructor(
            id: Long?,
            iconResId: Int,
            key: String,
            value: String,
            isShown: Boolean
    ) : this(
            iconResId,
            key,
            value,
            isShown
    ) {
        this.id = id
        this.iconResId = iconResId
        this.key = key
        this.value = value
        this.isShown = isShown
    }

    override fun toString(): String =
            "DetailModel(id=$id, iconResId=$iconResId, key='$key', value='$value', isShown=$isShown)"


}