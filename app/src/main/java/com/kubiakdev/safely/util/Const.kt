package com.kubiakdev.safely.util

import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.ui.icon.adapter.item.IconItem

object Const {

    val DEFAULT_TEMPLATE_LIST: List<TemplateModel> = listOf(
            TemplateModel(R.drawable.ic_title, "Title"),
            TemplateModel(R.drawable.ic_person, "Username"),
            TemplateModel(R.drawable.ic_person, "Login"),
            TemplateModel(R.drawable.ic_email, "Email"),
            TemplateModel(R.drawable.ic_lock, "Password"),
            TemplateModel(R.drawable.ic_credit_card, "Card number"),
            TemplateModel(R.drawable.ic_account_balance, "Account number"),
            TemplateModel(R.drawable.ic_description, "Description"),
            TemplateModel(R.drawable.ic_edit, "Custom")
    )

    val DEFAULT_ICON_LIST: MutableList<IconItem> = mutableListOf(
            IconItem(R.drawable.ic_image),
            IconItem(R.drawable.ic_person),
            IconItem(R.drawable.ic_perm_identity),
            IconItem(R.drawable.ic_credit_card),
            IconItem(R.drawable.ic_account_balance),
            IconItem(R.drawable.ic_account_balance_wallet),
            IconItem(R.drawable.ic_menu),
            IconItem(R.drawable.ic_edit),
            IconItem(R.drawable.ic_description),
            IconItem(R.drawable.ic_lock),
            IconItem(R.drawable.ic_email),
            IconItem(R.drawable.ic_visibility_on),
            IconItem(R.drawable.ic_copy),
            IconItem(R.drawable.ic_more),
            IconItem(R.drawable.ic_save)
    )

}