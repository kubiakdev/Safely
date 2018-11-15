package com.kubiakdev.safely.data.model

import java.util.*

data class PasswordModel(
        var title: String = "",
        var jsonDetailList: String = "",
        var created: Date = Date(),
        var modified: Date = Date(),
        var isFavourite: Boolean = false,
        var cardColor: String = "",
        var category: String = ""
) : Model {

    var id: Long = 0

    constructor(
            id: Long = 0,
            title: String = "",
            jsonDetailList: String = "",
            created: Date = Date(),
            modified: Date = Date(),
            isFavourite: Boolean = false,
            cardColor: String = "",
            category: String = ""
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
