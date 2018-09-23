package com.kubiakdev.safely.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<P : BasePresenter<*>> : DaggerFragment(), BaseView {

    @Inject
    lateinit var presenter: P
        internal set

    abstract val layoutId : Int

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun onDetach() {
        presenter.onDetach()
        super.onDetach()
    }

    protected open fun inflateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)

    open fun initComponents() {}

    fun showMessage(message: String) {
        (activity as BaseActivity<*>).showMessage(message)
    }
}