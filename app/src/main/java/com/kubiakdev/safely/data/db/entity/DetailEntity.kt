package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DetailEntity(var iconResId: Int = 0,
                        var keyResId: Int = 0) {

    @Id
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