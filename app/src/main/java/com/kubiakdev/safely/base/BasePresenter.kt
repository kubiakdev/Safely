package com.kubiakdev.safely.base

import kotlinx.coroutines.experimental.Job

open class BasePresenter<V : BaseView> {

    var view: V? = null

    protected var parentJob = Job(null)

    open fun onDetach() {
        parentJob.cancel()
        view = null
    }
}
