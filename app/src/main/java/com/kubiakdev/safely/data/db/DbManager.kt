package com.kubiakdev.safely.data.db

import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.db.entity.CipherEntity
import com.kubiakdev.safely.data.db.entity.DetailEntity
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable

class DbManager : DataManager {

    override val boxStore: BoxStore = BoxStore.getDefault()

    override var allDetailEntities: MutableList<DetailEntity> =
            BoxStore.getDefault().boxFor(DetailEntity::class.java).all
        set(collection) {
            BoxStore.getDefault().boxFor(DetailEntity::class.java).run {
                removeAll()
                put(collection)
            }
        }

    override var allPasswordEntities: MutableList<PasswordEntity> =
            BoxStore.getDefault().boxFor(PasswordEntity::class.java).all
        set(collection) {
            BoxStore.getDefault().boxFor(PasswordEntity::class.java).run {
                removeAll()
                put(collection)
            }
        }

    override val cipherBox: Observable<Box<CipherEntity>> =
            Observable.fromCallable { BoxStore.getDefault().boxFor(CipherEntity::class.java) }

    override val passwordBox: Observable<Box<PasswordEntity>> = Observable.fromCallable {
        BoxStore.getDefault().boxFor(PasswordEntity::class.java)
    }

    override val allCipherEntities: Observable<List<CipherEntity>> = Observable.fromCallable {
        BoxStore.getDefault().boxFor(CipherEntity::class.java).all
    }

    override fun add(entity: CipherEntity): Observable<Long> = Observable.fromCallable {
        BoxStore.getDefault().boxFor(CipherEntity::class.java).put(entity)
    }

    override fun add(entity: PasswordEntity) =
        BoxStore.getDefault().boxFor(PasswordEntity::class.java).put(entity)

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
//                        .equal(PasswordEntity_.isFavourite, entity.isFavourite)
                        .build().findFirst()
            }

    override fun getPasswordEntityByModified(modified: String): Observable<PasswordEntity> =
            Observable.fromCallable {
                BoxStore.getDefault().boxFor(PasswordEntity::class.java).query()
//                        .equal(PasswordEntity_.modified, modified)
                        .build().findFirst()
            }
}