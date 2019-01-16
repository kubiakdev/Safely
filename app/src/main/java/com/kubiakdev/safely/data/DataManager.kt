package com.kubiakdev.safely.data

import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import com.kubiakdev.safely.data.db.entity.TemplateEntity
import io.objectbox.Box
import io.objectbox.BoxStore

interface DataManager {

    val boxStore: BoxStore

    val passwordBox: Box<PasswordEntity>

    val detailBox: Box<DetailEntity>

    val templateBox: Box<TemplateEntity>

}
