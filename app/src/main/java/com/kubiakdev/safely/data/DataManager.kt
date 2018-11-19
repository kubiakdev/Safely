package com.kubiakdev.safely.data

import com.kubiakdev.safely.data.db.entity.CipherEntity
import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable

interface DataManager {

    val boxStore: BoxStore

    var allDetailEntities: MutableList<DetailEntity>

    var allPasswordEntities: MutableList<PasswordEntity>

    val cipherBox: Observable<Box<CipherEntity>>

    val passwordBox: Observable<Box<PasswordEntity>>

    val allCipherEntities: Observable<List<CipherEntity>>

    fun add(entity: CipherEntity): Observable<Long>

    fun add(entity: PasswordEntity): Long

    fun getPasswordEntity(entity: PasswordEntity): Observable<PasswordEntity>

    fun getPasswordEntityByModified(modified: String): Observable<PasswordEntity>
}
