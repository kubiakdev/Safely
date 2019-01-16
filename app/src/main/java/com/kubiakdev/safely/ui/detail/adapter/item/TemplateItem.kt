package com.kubiakdev.safely.ui.detail.adapter.item

import com.kubiakdev.safely.base.adapter.ListItem

data class TemplateItem(
        var iconResId: Int,
        var key: String
) : ListItem {

    var id: Long = 0

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