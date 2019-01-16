package com.kubiakdev.safely.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.ui.main.MainView
import com.kubiakdev.safely.util.extension.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity : DaggerAppCompatActivity(), MainView {

    private val layoutId: Int = R.layout.activity_base

    private val navControllerId: Int = R.id.navHost

    private lateinit var navController: NavController

    private val snackbar by lazy {
        Snackbar.make(clSnackbar, "", Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        navController = Navigation.findNavController(this, navControllerId)
        setSupportActionBar(bottomAppBar)
    }

    override fun onPause() {
        super.onPause()
        currentFocus?.hideKeyboard()
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun showSnackbar(@StringRes stringResId: Int) {
        snackbar
                .setText(stringResId)
                .show()
    }

    fun dismissSnackbar() {
        snackbar.dismiss()
    }

}
