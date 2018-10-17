package com.kubiakdev.safely.ui.main

import com.kubiakdev.safely.di.annotation.FragmentScope
import com.kubiakdev.safely.di.module.ActivityModule
import com.kubiakdev.safely.ui.main.fragment.detail.DetailFragment
import com.kubiakdev.safely.ui.main.fragment.detail.DetailModule
import com.kubiakdev.safely.ui.main.fragment.icon.IconFragment
import com.kubiakdev.safely.ui.main.fragment.icon.IconModule
import com.kubiakdev.safely.ui.main.fragment.main.MainFragment
import com.kubiakdev.safely.ui.main.fragment.main.MainModule
import com.kubiakdev.safely.ui.main.fragment.password.PasswordFragment
import com.kubiakdev.safely.ui.main.fragment.password.PasswordModule
import com.kubiakdev.safely.ui.main.fragment.template.TemplateFragment
import com.kubiakdev.safely.ui.main.fragment.template.TemplateModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ActivityModule::class])
abstract class MainModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [DetailModule::class])
    abstract fun provideDetailModuleInjector(): DetailFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [IconModule::class])
    abstract fun provideIconModuleInjector(): IconFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun provideMainModuleInjector(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [PasswordModule::class])
    abstract fun providePasswordModuleInjector(): PasswordFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TemplateModule::class])
    abstract fun provideTemplateModuleInjector(): TemplateFragment
}