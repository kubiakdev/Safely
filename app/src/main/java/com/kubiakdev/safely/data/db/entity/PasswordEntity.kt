package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Id

data class PasswordEntity(
        var jsonDetailList: String = "",
        var isBookmarked: Boolean = false,
        var cardColor: String = "",
        var category: String = ""
) {

    @Id(assignable = true)
    var id: Long = 0

    constructor(
            id: Long = 0,
            jsonDetailList: String = "",
            isBookmarked: Boolean = false,
            cardColor: String = "",
            category: String = ""
    ) : this(
            jsonDetailList,
            isBookmarked,
            cardColor,
            category
    ) {
        this.id = id
        this.jsonDetailList = jsonDetailList
        this.isBookmarked = isBookmarked
        this.cardColor = cardColor
        this.category = category
    }
}
