package com.kubiakdev.safely.data.mapper

import com.kubiakdev.safely.data.db.entity.PasswordEntity
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.data.model.PasswordModel
import com.kubiakdev.safely.ui.main.adapter.item.PasswordItem
import org.json.JSONArray
import org.json.JSONObject

private const val ID = "id"
private const val ICON_RES_ID = "iconResId"
private const val IS_SHOWN = "isShown"
private const val KEY = "key"
private const val VALUE = "value"

fun entityToModel(entity: PasswordEntity) = PasswordModel(
        entity.id,
        entity.title,
        mapJsonToDetailList(entity.jsonDetailList),
        entity.created,
        entity.modified,
        entity.isFavourite,
        entity.cardColor,
        entity.category
)

fun modelToEntity(model: PasswordModel) = PasswordEntity(
        model.id,
        model.title,
        mapDetailListToJson(model.detailList),
        model.created,
        model.modified,
        model.isFavourite,
        model.cardColor,
        model.category
)

fun modelToItem(model: PasswordModel) = PasswordItem(
        model.id,
        model.title,
        model.detailList,
        model.created,
        model.modified,
        model.isFavourite,
        model.cardColor,
        model.category
)

fun itemToEntity(item: PasswordItem) = PasswordEntity(
        item.id,
        item.title,
        mapDetailListToJson(item.detailList),
        item.created,
        item.modified,
        item.isFavourite,
        item.cardColor,
        item.category
)


fun mapJsonToDetailList(json: String): List<DetailModel> =
        mutableListOf<DetailModel>().apply {
            JSONArray(json).let {
                for (i in 0 until it.length()) {
                    it.getJSONObject(i).apply {
                        add(
                                DetailModel(
                                        getLong(ID),
                                        getInt(ICON_RES_ID),
                                        getString(KEY),
                                        getString(VALUE),
                                        getBoolean(IS_SHOWN)
                                )
                        )
                    }
                }
            }
        }

fun mapDetailListToJson(detailList: List<DetailModel>): String =
        JSONArray().apply {
            detailList.forEach {
                put(
                        JSONObject()
                                .put(ID, it.id)
                                .put(ICON_RES_ID, it.iconResId)
                                .put(IS_SHOWN, it.isShown)
                                .put(KEY, it.key)
                                .put(VALUE, it.value)
                )
            }
        }.toString()
