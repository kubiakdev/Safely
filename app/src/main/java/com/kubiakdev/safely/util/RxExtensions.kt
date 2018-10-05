package com.kubiakdev.safely.util

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Completable.applyTransformations(scheduler: Scheduler = Schedulers.io()): Completable =
        doOnError { it.printStackTrace() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(scheduler)

fun <T> Observable<T>.applyTransformations(scheduler: Scheduler = Schedulers.io()): Observable<T> =
        doOnError { it.printStackTrace() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(scheduler)

fun <T> Single<T>.applyTransformations(scheduler: Scheduler = Schedulers.io()): Single<T> =
        doOnError { it.printStackTrace() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(scheduler)
