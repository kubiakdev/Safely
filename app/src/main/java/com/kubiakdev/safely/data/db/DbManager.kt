package com.kubiakdev.safely.data.db

import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import com.kubiakdev.safely.data.db.entity.TemplateEntity
import io.objectbox.Box
import io.objectbox.BoxStore

class DbManager : DataManager {

    override val boxStore: BoxStore = BoxStore.getDefault()

    override val detailBox: Box<DetailEntity> = boxStore.boxFor(DetailEntity::class.java)

    override val passwordBox: Box<PasswordEntity> = boxStore.boxFor(PasswordEntity::class.java)

    override val templateBox: Box<TemplateEntity> = boxStore.boxFor(TemplateEntity::class.java)

}