package com.kubiakdev.safely.ui.main.fragment.main

import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.mvp.BaseFragment
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.fragment.main.mvp.main.MainPresenter
import com.kubiakdev.safely.ui.main.fragment.main.mvp.main.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainFragment : BaseFragment<MainPresenter>(), MainView {

    override val layoutId: Int = R.layout.fragment_main

    override fun initComponents() {
        activity?.bar_main?.setNavigationIcon(R.drawable.ic_menu)
        activity?.bar_main?.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        activity?.bar_main?.replaceMenu(R.menu.menu_main)
        activity?.fab_main?.setImageResource(R.drawable.ic_add)
        activity?.fab_main?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_passwordFragment)
        }
    }

    override fun doOnMenuActionClick(action: String, value: Boolean) {}
}