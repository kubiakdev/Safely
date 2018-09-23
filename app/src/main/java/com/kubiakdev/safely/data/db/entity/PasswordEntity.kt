package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Id

data class PasswordEntity(
        var title: String = "",
        var username: String = "",
        var login: String = "",
        var email: String = "",
        var password: String = "",
        var webAddress: String = "",
        var jsonString: String = "",
        var created: String = "",
        var modified: String = "",
        var isBookmarked: Boolean = false,
        var cardColor: String = "",
        var category: String = ""
) {

    @Id(assignable = true)
    var id: Long = 0

    constructor(
            id: Long = 0,
            title: String = "",
            username: String = "",
            login: String = "",
            email: String = "",
            password: String = "",
            webAddress: String = "",
            jsonString: String = "",
            created: String = "",
            modified: String = "",
            isBookmarked: Boolean = false,
            cardColor: String = "",
            category: String = ""
    ) : this(
            title,
            username,
            login,
            email,
            password,
            webAddress,
            jsonString,
            created,
            modified,
            isBookmarked,
            cardColor,
            category
    ) {
        this.id = id
    }
}
