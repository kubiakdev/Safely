package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DetailEntity(
        var iconResId: Int,
        var key: String,
        var value: String,
        var isShown: Boolean
) {

    @Id
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