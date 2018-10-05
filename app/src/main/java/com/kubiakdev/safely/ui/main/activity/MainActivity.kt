package com.kubiakdev.safely.ui.main.activity

import android.view.Menu
import android.view.MenuItem
import com.kubiakdev.safely.R
import com.kubiakdev.safely.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override val layoutId: Int = R.layout.activity_main

    override val navControllerId: Int = R.id.nav_host_main

    override fun setActionBar() {
        setSupportActionBar(bar_main)
        presenter.getC().subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_detail_add -> {
                navController.navigate(R.id.action_detailFragment_to_passwordFragment)
//                return true
            }

            R.id.action_password_add -> {
                navController.navigate(R.id.action_passwordFragment_to_addFragment)
//                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }
}
