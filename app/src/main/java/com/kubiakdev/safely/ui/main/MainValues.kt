package com.kubiakdev.safely.ui.main

import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel


object MainValues {

    val DETAIL_DEFAULT_TEMPLATE_LIST: MutableList<DetailModel> =
            mutableListOf(
                    DetailModel(R.drawable.ic_title, "Title"),
                    DetailModel(R.drawable.ic_person, "Username"),
                    DetailModel(R.drawable.ic_person, "Login"),
                    DetailModel(R.drawable.ic_email, "Email"),
                    DetailModel(R.drawable.ic_lock, "Password"),
                    DetailModel(R.drawable.ic_credit_card, "Card number"),
                    DetailModel(R.drawable.ic_account_balance, "Account number"),
                    DetailModel(R.drawable.ic_description, "Description"),
                    DetailModel(R.drawable.ic_edit, "Custom")
            )

    const val EXTRA_DETAIL_ICON_RES_ID = "EXTRA_DETAIL_ICON_RES_ID"
    const val EXTRA_DETAIL_KEY = "EXTRA_DETAIL_KEY"
    const val EXTRA_TEMPLATE_ICON_RES_ID = "EXTRA_TEMPLATE_ICON_RES_ID"
    const val EXTRA_TEMPLATE_KEY = "EXTRA_TEMPLATE_KEY"

    const val ACTION_TEMPLATE_SAVE = "ACTION_TEMPLATE_SAVE"
    const val ACTION_PASSWORD_DELETE = "ACTION_PASSWORD_DELETE"
}