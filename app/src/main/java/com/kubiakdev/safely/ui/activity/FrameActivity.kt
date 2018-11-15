package com.kubiakdev.safely.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.FragmentListener
import com.kubiakdev.safely.ui.main.MainView
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.extension.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : DaggerAppCompatActivity(), MainView {

    private val layoutId: Int = R.layout.activity_frame

    private val navControllerId: Int = R.id.nav_host_frame

    var fragmentListener: FragmentListener? = null

    var menu: Menu? = null

    private lateinit var navController: NavController

    private lateinit var snackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        navController = Navigation.findNavController(this, navControllerId).apply {
            addOnNavigatedListener { _, destination -> tv_frame.text = destination.label }
        }
        setSupportActionBar(bar_frame)
    }

    override fun onStop() {
        currentFocus?.hideKeyboard()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        bar_frame.hideKeyboard()
        fragmentListener?.doOnMenuActionClick(
                when (item.itemId) {
                    R.id.action_password_add -> {
                        Const.ACTION_PASSWORD_ADD
                    }
                    R.id.action_detail_add -> {
                        Const.ACTION_DETAIL_ADD
                    }
                    R.id.action_detail_delete -> {
                        Const.ACTION_DETAIL_DELETE
                    }
                    R.id.action_detail_edit -> {
                        Const.ACTION_DETAIL_EDIT
                    }
                    R.id.action_template_save -> {
                        Const.ACTION_TEMPLATE_SAVE
                    }
                    R.id.action_password_delete -> {
                        Const.ACTION_PASSWORD_DELETE
                    }
                    R.id.action_password_save -> {
                        Const.ACTION_PASSWORD_SAVE
                    }
                    else -> {
                        ""
                    }
                }
        )
        return super.onOptionsItemSelected(item)
    }

    fun hideProgressBar() {
        pb_frame.visibility = View.GONE
    }

    fun showProgressBar() {
        pb_frame.visibility = View.VISIBLE
    }

    fun showSnackBar(@StringRes stringRes: Int) {
        snackBar = Snackbar.make(
                findViewById(R.id.cl_frame_snack), stringRes, Snackbar.LENGTH_SHORT
        ).apply { show() }
    }

    fun dismissSnackBar() {
        snackBar.dismiss()
    }
}
