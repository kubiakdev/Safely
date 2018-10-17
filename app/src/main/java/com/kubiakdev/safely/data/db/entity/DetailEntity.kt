package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DetailEntity(var iconResId: Int = 0,
                        var key: String = "") {

    @Id
    var id: Long? = 0

    var value: String= ""
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
            key : String,
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