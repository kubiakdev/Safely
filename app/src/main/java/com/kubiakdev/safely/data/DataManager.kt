package com.kubiakdev.safely.data

import com.kubiakdev.safely.data.db.entity.CipherEntity
import com.kubiakdev.safely.data.db.entity.NoteEntity
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import io.objectbox.Box
import io.reactivex.Observable

interface DataManager {

    val cipherBox: Observable<Box<CipherEntity>>

    val noteBox: Observable<Box<NoteEntity>>

    val passwordBox: Observable<Box<PasswordEntity>>

    val allCipherEntities: Observable<CipherEntity>

    val allNoteEntities: Observable<NoteEntity>

    val allPasswordEntities: Observable<PasswordEntity>

    fun add(entity: CipherEntity): Observable<Long>

    fun add(entity: NoteEntity): Observable<Long>

    fun add(entity: PasswordEntity): Observable<Long>

    fun getNoteEntity(entity: NoteEntity): Observable<NoteEntity>

    fun getPasswordEntity(entity: PasswordEntity): Observable<PasswordEntity>

    fun getPasswordEntityByModified(modified: String): Observable<PasswordEntity>
}
