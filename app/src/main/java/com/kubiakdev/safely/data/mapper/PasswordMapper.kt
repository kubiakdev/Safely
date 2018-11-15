package com.kubiakdev.safely.data.mapper

import com.kubiakdev.safely.data.db.entity.PasswordEntity
import com.kubiakdev.safely.data.model.PasswordModel

fun entityToModel(entity: PasswordEntity): PasswordModel = PasswordModel(
        entity.id,
        entity.title,
        entity.jsonDetailList,
        entity.created,
        entity.modified,
        entity.isFavourite,
        entity.cardColor,
        entity.category
)

fun modelToEntity(model: PasswordModel): PasswordEntity = PasswordEntity(
        model.id,
        model.title,
        model.jsonDetailList,
        model.created,
        model.modified,
        model.isFavourite,
        model.cardColor,
        model.category
)