package com.kubiakdev.safely.ui.main

import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel


object MainValues {

    val DETAIL_DEFAULT_TEMPLATE_LIST: MutableList<DetailModel> =
            mutableListOf(
                    DetailModel(R.drawable.ic_title, R.string.all_new_title),
                    DetailModel(R.drawable.ic_person, R.string.all_new_username),
                    DetailModel(R.drawable.ic_person, R.string.all_new_login),
                    DetailModel(R.drawable.ic_email, R.string.all_new_email),
                    DetailModel(R.drawable.ic_lock, R.string.all_new_password),
                    DetailModel(R.drawable.ic_credit_card, R.string.all_new_card_number),
                    DetailModel(R.drawable.ic_account_balance, R.string.all_new_account_number),
                    DetailModel(R.drawable.ic_description, R.string.all_new_description),
                    DetailModel(R.drawable.ic_edit, R.string.all_new_custom)
            )
}