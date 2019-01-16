package com.kubiakdev.safely.di.module

import com.kubiakdev.safely.base.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun bindMainActivity(): BaseActivity

}