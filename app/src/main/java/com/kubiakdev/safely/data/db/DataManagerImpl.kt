package com.kubiakdev.safely.data.db

import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.db.entity.CipherEntity
import com.kubiakdev.safely.data.db.entity.NoteEntity
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable

class DataManagerImpl : DataManager {

    override val cipherBox: Observable<Box<CipherEntity>>
        get() = Observable.fromCallable { BoxStore.getDefault().boxFor(CipherEntity::class.java) }

    override val noteBox: Observable<Box<NoteEntity>>
        get() = Observable.fromCallable { BoxStore.getDefault().boxFor(NoteEntity::class.java) }

    override val passwordBox: Observable<Box<PasswordEntity>>
        get() = Observable.fromCallable { BoxStore.getDefault().boxFor(PasswordEntity::class.java) }

    override val allCipherEntities: Observable<CipherEntity>
        get() = Observable.fromCallable { BoxStore.getDefault().boxFor(CipherEntity::class.java).all }
                .flatMapIterable { cipherEntities -> cipherEntities }

    override val allNoteEntities: Observable<NoteEntity>
        get() = Observable.fromCallable { BoxStore.getDefault().boxFor(NoteEntity::class.java).all }
                .flatMapIterable { contentEntities -> contentEntities }

    override val allPasswordEntities: Observable<PasswordEntity>
        get() = Observable.fromCallable { BoxStore.getDefault().boxFor(PasswordEntity::class.java).all }
                .flatMapIterable { passwordEntities -> passwordEntities }

    override fun add(entity: CipherEntity): Observable<Long> = Observable.fromCallable {
        BoxStore.getDefault().boxFor(CipherEntity::class.java).put(entity)
    }

    override fun add(entity: NoteEntity): Observable<Long> = Observable.fromCallable {
        BoxStore.getDefault().boxFor(NoteEntity::class.java).put(entity)
    }

    override fun add(entity: PasswordEntity): Observable<Long> = Observable.fromCallable {
        BoxStore.getDefault().boxFor(PasswordEntity::class.java).put(entity)
    }

    override fun getNoteEntity(entity: NoteEntity): Observable<NoteEntity> =
            Observable.fromCallable {
                BoxStore.getDefault().boxFor(NoteEntity::class.java).query()
//                        .equal(NoteEntity_.content, entity.content!!)
//                        .equal(NoteEntity_.created, entity.created!!)
//                        .equal(NoteEntity_.modified, entity.modified!!)
//                        .equal(NoteEntity_.isBookmarked, entity.isBookmarked)
                        .build().findFirst()
            }

    override fun getPasswordEntity(entity: PasswordEntity): Observable<PasswordEntity> =
            Observable.fromCallable {
                BoxStore.getDefault().boxFor(PasswordEntity::class.java).query()
//                        .equal(PasswordEntity_.title, entity.title)
//                        .equal(PasswordEntity_.username, entity.username)
//                        .equal(PasswordEntity_.login, entity.login)
//                        .equal(PasswordEntity_.email, entity.email)
//                        .equal(PasswordEntity_.password, entity.password)
//                        .equal(PasswordEntity_.webAddress, entity.password)
//                        .equal(PasswordEntity_.jsonString, entity.jsonString)
//                        .equal(PasswordEntity_.created, entity.created)
//                        .equal(PasswordEntity_.modified, entity.modified)
//                        .equal(PasswordEntity_.isBookmarked, entity.isBookmarked)
                        .build().findFirst()
            }

    override fun getPasswordEntityByModified(modified: String): Observable<PasswordEntity> =
            Observable.fromCallable {
                BoxStore.getDefault().boxFor(PasswordEntity::class.java).query()
//                        .equal(PasswordEntity_.modified, modified)
                        .build().findFirst()
            }
}