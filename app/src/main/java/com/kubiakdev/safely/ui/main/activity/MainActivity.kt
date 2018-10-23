package com.kubiakdev.safely.ui.main.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseActivity
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.util.KeyboardUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override val layoutId: Int = R.layout.activity_main

    override val navControllerId: Int = R.id.nav_host_main

    var isInDeletePasswordMode = false
    private var menu: Menu? = null

    override fun setActionBar() {
        setSupportActionBar(bar_main)
    }

    override fun onAttach() {
        presenter.view = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        KeyboardUtil.hideKeyboard(currentFocus ?: View(this))
        when (item?.itemId) {
            R.id.action_password_add -> {
                navController.navigate(R.id.action_passwordFragment_to_detailFragment)
                return true
            }
            R.id.action_detail_add -> {
                navController.navigate(R.id.action_detailFragment_to_templateFragment)
                return true
            }
            R.id.action_template_save -> {
                fragmentListener?.doOnMenuActionClick(MainValues.ACTION_TEMPLATE_SAVE)
            }
            R.id.action_password_delete -> {
                isInDeletePasswordMode = !isInDeletePasswordMode
                switchDeleteMode()
                fragmentListener
                        ?.doOnMenuActionClick(
                                MainValues.ACTION_PASSWORD_DELETE,
                                isInDeletePasswordMode
                        )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun hideProgressBar() {
        pb_main.visibility = View.GONE
    }

    override fun showProgressBar() {
        pb_main.visibility = View.VISIBLE
    }

    override fun showSnackBar(@StringRes stringRes: Int) {
        Snackbar.make(findViewById(R.id.cl_main_snack), stringRes, Snackbar.LENGTH_SHORT).show()
    }

    fun switchDeleteMode() {
        menu?.get(0)?.icon = if (isInDeletePasswordMode) {
            ContextCompat.getDrawable(this, R.drawable.ic_delete_on).also {
                showSnackBar(R.string.password_delete_on)
            }
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_delete_off)
        }
    }
}
