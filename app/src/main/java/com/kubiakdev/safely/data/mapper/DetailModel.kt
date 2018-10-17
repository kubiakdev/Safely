package com.kubiakdev.safely.data.mapper

import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.model.DetailModel

fun mapEntityToModel(entity: DetailEntity): DetailModel = DetailModel(
        entity.id,
        entity.iconResId,
        entity.key,
        entity.value,
        entity.isShown
)

fun mapModelToEntity(model: DetailModel): DetailEntity = DetailEntity(
        model.id,
        model.iconResId,
        model.key,
        model.value,
        model.isShown
)