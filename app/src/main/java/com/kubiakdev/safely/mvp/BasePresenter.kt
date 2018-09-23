package com.kubiakdev.safely.mvp

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V : BaseView> {

    var view: V? = null

    protected var compositeDisposable = CompositeDisposable()

    fun onDetach() {
        compositeDisposable.clear()
    }
}
