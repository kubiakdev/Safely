package com.kubiakdev.safely.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kubiakdev.safely.util.extension.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), BaseView {

    var fragmentListener: FragmentListener? = null

    protected abstract val layoutId: Int

    protected abstract val navControllerId: Int

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        navController = Navigation.findNavController(this, navControllerId)
        launchFragment()
        setActionBar()
    }

    override fun onStop() {
        currentFocus?.hideKeyboard()
        super.onStop()
    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    abstract fun hideProgressBar()

    abstract fun showProgressBar()

    abstract fun showSnackBar(@StringRes stringRes: Int)

    abstract fun dismissSnackBar()

    open fun setActionBar() {}

    protected open fun launchFragment() {}
}