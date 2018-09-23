package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class NoteEntity(
        var content: String = "",
        var created: String = "",
        var modified: String = "",
        var isBookmarked: Boolean = false,
        var cardColor: String = "",
        var category: String = "") {

    @Id(assignable = true)
    var id: Long = 0

    constructor(
            id: Long = 0,
            content: String = "",
            created: String = "",
            modified: String = "",
            isBookmarked: Boolean = false,
            cardColor: String = "",
            category: String = ""
    ) : this(
            content,
            created,
            modified,
            isBookmarked,
            cardColor,
            category
    ) {
        this.id = id
    }
}
