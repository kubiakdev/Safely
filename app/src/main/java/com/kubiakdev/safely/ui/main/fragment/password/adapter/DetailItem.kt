package com.kubiakdev.safely.ui.main.fragment.password.adapter

class DetailItem(
        var iconRes: Int?,
        var key: String,
        var value: String,
        var isShown: Boolean
) {

    var id: Long = 0

    constructor(
            id: Long = 0,
            iconRes: Int?,
            key: String,
            value: String,
            isShown: Boolean
    ) : this(
            iconRes,
            key,
            value,
            isShown
    )
}