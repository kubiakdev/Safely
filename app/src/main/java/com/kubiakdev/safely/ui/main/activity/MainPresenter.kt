package com.kubiakdev.safely.ui.main.activity

import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.mvp.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    @Inject
    lateinit var dataManager: DataManager

    fun getC() = dataManager.getAllDetailEntities()
}