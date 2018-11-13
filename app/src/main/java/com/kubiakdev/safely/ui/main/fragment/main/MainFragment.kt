package com.kubiakdev.safely.ui.main.fragment.main

import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainFragment : BaseFragment(), MainView {

    override val layoutId: Int = R.layout.fragment_main

    override fun initActivityComponents() {
        activity?.run {
            bar_main?.run {
                replaceMenu(R.menu.menu_main)
                setNavigationIcon(R.drawable.ic_menu)
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
            fab_main?.run {
                setImageResource(R.drawable.ic_add)
                setOnClickListener {
                    findNavController().navigate(R.id.action_mainFragment_to_passwordFragment)
                }
            }
        }
    }
}