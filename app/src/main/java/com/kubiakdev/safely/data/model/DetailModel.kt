package com.kubiakdev.safely.data.model

data class DetailModel(
        var iconResId: Int,
        var key: String,
        var value: String,
        var isShown: Boolean
) : Model {

    var id: Long = 0

    constructor(
            id: Long,
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

}
