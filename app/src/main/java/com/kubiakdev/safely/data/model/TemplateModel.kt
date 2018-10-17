package com.kubiakdev.safely.data.model

data class TemplateModel(var iconResId: Int = 0, var key: String = "") : Model {

    var id: Long? = 0

    constructor(
            id: Long,
            iconResId: Int,
            key: String
    ) : this(
            iconResId,
            key
    ) {
        this.id = id
        this.iconResId = iconResId
        this.key = key
    }
}