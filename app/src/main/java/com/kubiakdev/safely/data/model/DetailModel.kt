package com.kubiakdev.safely.data.model

data class DetailModel(var iconResId: Int,
                        var keyResId: Int) {

    var id: Long? = 0

    var value: String? = ""
    var isShown: Boolean? = true

    constructor(
            iconResId: Int,
            keyResId: Int,
            value: String?,
            isShown: Boolean?
    ) : this(
            iconResId,
            keyResId
    ) {
        this.iconResId = iconResId
        this.keyResId = keyResId
        this.value = value
        this.isShown = isShown
    }

    constructor(
            id: Long?,
            iconResId: Int,
            keyResId: Int,
            value: String?,
            isShown: Boolean?
    ) : this(
            iconResId,
            keyResId,
            value,
            isShown
    ) {
        this.id = id
        this.iconResId = iconResId
        this.keyResId = keyResId
        this.value = value
        this.isShown = isShown
    }
}