package com.kubiakdev.safely.ui.main.fragment.main

import com.kubiakdev.safely.R
import com.kubiakdev.safely.mvp.BaseFragment
import com.kubiakdev.safely.ui.main.fragment.main.mvp.main.MainPresenter
import com.kubiakdev.safely.ui.main.fragment.main.mvp.main.MainView

class MainFragment : BaseFragment<MainPresenter>(), MainView {

    override val layoutId: Int = R.layout.fragment_main
}