package com.kubiakdev.safely.ui.main

import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.data.model.IconModel


object MainValues {

    val DEFAULT_TEMPLATE_LIST: MutableList<DetailModel> =
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

    val DEFAULT_ICON_LIST: MutableList<IconModel> =
            mutableListOf(
                    IconModel(R.drawable.ic_image),
                    IconModel(R.drawable.ic_person),
                    IconModel(R.drawable.ic_perm_identity),
                    IconModel(R.drawable.ic_credit_card),
                    IconModel(R.drawable.ic_account_balance),
                    IconModel(R.drawable.ic_menu),
                    IconModel(R.drawable.ic_edit),
                    IconModel(R.drawable.ic_description),
                    IconModel(R.drawable.ic_lock),
                    IconModel(R.drawable.ic_email),
                    IconModel(R.drawable.ic_visibility),
                    IconModel(R.drawable.ic_copy),
                    IconModel(R.drawable.ic_more),
                    IconModel(R.drawable.ic_save)
            )

    const val EXTRA_DETAIL_ICON_RES_ID = "EXTRA_DETAIL_ICON_RES_ID"
    const val EXTRA_DETAIL_KEY = "EXTRA_DETAIL_KEY"
    const val EXTRA_TEMPLATE_ICON_RES_ID = "EXTRA_TEMPLATE_ICON_RES_ID"
    const val EXTRA_TEMPLATE_KEY = "EXTRA_TEMPLATE_KEY"
    const val EXTRA_ICON_RES_ID = "EXTRA_ICON_RES_ID"

    const val ACTION_TEMPLATE_SAVE = "ACTION_TEMPLATE_SAVE"
    const val ACTION_PASSWORD_DELETE = "ACTION_PASSWORD_DELETE"
}