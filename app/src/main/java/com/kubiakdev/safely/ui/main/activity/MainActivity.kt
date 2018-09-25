package com.kubiakdev.safely.ui.main.activity

import android.view.Menu
import com.kubiakdev.safely.R
import com.kubiakdev.safely.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override val layoutId: Int = R.layout.activity_main

    override val navControllerId: Int = R.id.nav_host_main

    override fun setActionBar() {
        setSupportActionBar(bar_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
