package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TemplateEntity(
        var iconResId: Int = 0,
        var key: String = ""
) {

    @Id
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