package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class PasswordEntity(
        var title: String,
        var jsonDetailList: String,
        var created: Date,
        var modified: Date,
        var isFavourite: Boolean,
        var cardColor: String,
        var category: String
) {

    @Id(assignable = true)
    var id: Long = 0

    constructor(
            id: Long,
            title: String,
            jsonDetailList: String,
            created: Date,
            modified: Date,
            isFavourite: Boolean,
            cardColor: String,
            category: String
    ) : this(
            title,
            jsonDetailList,
            created,
            modified,
            isFavourite,
            cardColor,
            category
    ) {
        this.id = id
        this.title = title
        this.jsonDetailList = jsonDetailList
        this.created = created
        this.modified = modified
        this.isFavourite = isFavourite
        this.cardColor = cardColor
        this.category = category
    }

}