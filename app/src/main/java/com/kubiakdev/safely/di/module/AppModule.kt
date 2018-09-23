package com.kubiakdev.safely.di.module

import com.kubiakdev.safely.ui.main.activity.MainActivity
import com.kubiakdev.safely.di.annotation.ActivityScope
import com.kubiakdev.safely.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity
}