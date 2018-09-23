package com.kubiakdev.safely.mvp

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<P : BasePresenter<*>> : DaggerAppCompatActivity(), BaseView {

    @Inject
    lateinit var presenter: P

    protected abstract val layoutId: Int

    protected abstract val navControllerId: Int

    protected lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        navController = Navigation.findNavController(this, navControllerId)
        launchFragment()
        setActionBar()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected open fun launchFragment() {}

    open fun setActionBar() {}
}
