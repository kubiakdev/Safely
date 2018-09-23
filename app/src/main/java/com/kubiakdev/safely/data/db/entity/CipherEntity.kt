package com.kubiakdev.safely.data.db.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class CipherEntity(
        var key: String = "",
        var value: String = "") {

    @Id
    var id: Long = 0

    constructor(
            id: Long = 0,
            key: String = "",
            value: String = ""
    ) : this(key, value) {
        this.id = id
    }
}