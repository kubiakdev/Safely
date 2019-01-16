package com.kubiakdev.safely.ui.password.adapter.item

import com.kubiakdev.safely.base.adapter.ListItem

data class DetailItem(
        var iconResId: Int,
        var key: String,
        var value: String,
        var isShown: Boolean
) : ListItem {

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
