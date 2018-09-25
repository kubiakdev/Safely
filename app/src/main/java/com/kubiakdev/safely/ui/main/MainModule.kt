package com.kubiakdev.safely.ui.main

import com.kubiakdev.safely.di.annotation.FragmentScope
import com.kubiakdev.safely.di.module.ActivityModule
import com.kubiakdev.safely.ui.main.fragment.main.MainFragment
import com.kubiakdev.safely.ui.main.fragment.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ActivityModule::class])
abstract class MainModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun provideMainModuleInjector(): MainFragment
}