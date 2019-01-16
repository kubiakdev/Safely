package com.kubiakdev.safely.data.mapper

import com.kubiakdev.safely.data.db.entity.TemplateEntity
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.ui.detail.adapter.item.TemplateItem

fun entityToModel(entity: TemplateEntity) = TemplateModel(
        entity.id,
        entity.iconResId,
        entity.key
)

fun modelToEntity(model: TemplateModel) = TemplateEntity(
        model.id,
        model.iconResId,
        model.key
)

fun modelToItem(model: TemplateModel) = TemplateItem(
        model.id,
        model.iconResId,
        model.key
)

fun itemToEntity(item: TemplateItem) = TemplateEntity(
        item.id,
        item.iconResId,
        item.key
)

