package com.kubiakdev.safely.ui.main.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseActivity
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.util.extension.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    override val layoutId: Int = R.layout.activity_main

    override val navControllerId: Int = R.id.nav_host_main

    var menu: Menu? = null

    private lateinit var snackBar: Snackbar

    override fun setActionBar() {
        setSupportActionBar(bar_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        bar_main.hideKeyboard()
        fragmentListener?.doOnMenuActionClick(
                when (item?.itemId) {
                    R.id.action_password_add -> {
                        MainValues.ACTION_PASSWORD_ADD
                    }
                    R.id.action_detail_add -> {
                        MainValues.ACTION_DETAIL_ADD
                    }
                    R.id.action_detail_delete -> {
                        MainValues.ACTION_DETAIL_DELETE
                    }
                    R.id.action_detail_edit -> {
                        MainValues.ACTION_DETAIL_EDIT
                    }
                    R.id.action_template_save -> {
                        MainValues.ACTION_TEMPLATE_SAVE
                    }
                    R.id.action_password_delete -> {
                        MainValues.ACTION_PASSWORD_DELETE
                    }
                    R.id.action_password_save -> {
                        MainValues.ACTION_PASSWORD_SAVE
                    }
                    else -> {
                        ""
                    }
                }
        )
        return super.onOptionsItemSelected(item)
    }

    override fun hideProgressBar() {
        pb_main.visibility = View.GONE
    }

    override fun showProgressBar() {
        pb_main.visibility = View.VISIBLE
    }

    override fun showSnackBar(@StringRes stringRes: Int) {
        snackBar = Snackbar.make(
                findViewById(R.id.cl_main_snack), stringRes, Snackbar.LENGTH_SHORT
        ).apply { show() }
    }

    override fun dismissSnackBar() {
        snackBar.dismiss()
    }
}
