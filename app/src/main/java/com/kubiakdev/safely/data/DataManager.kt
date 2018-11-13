package com.kubiakdev.safely.data

import com.kubiakdev.safely.data.db.entity.CipherEntity
import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable

interface DataManager {

    val boxStore: BoxStore

    val detailBox: Box<DetailEntity>

    var allDetailEntities: List<DetailEntity>

    val cipherBox: Observable<Box<CipherEntity>>

    val passwordBox: Observable<Box<PasswordEntity>>

    val allCipherEntities: Observable<List<CipherEntity>>

    val allPasswordEntities: Observable<List<PasswordEntity>>

    fun add(entity: CipherEntity): Observable<Long>

    fun add(entity: PasswordEntity): Observable<Long>

    fun getPasswordEntity(entity: PasswordEntity): Observable<PasswordEntity>

    fun getPasswordEntityByModified(modified: String): Observable<PasswordEntity>
}
