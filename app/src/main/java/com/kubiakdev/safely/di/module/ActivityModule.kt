package com.kubiakdev.safely.di.module

import com.kubiakdev.safely.di.annotation.FragmentScope
import com.kubiakdev.safely.ui.detail.DetailFragment
import com.kubiakdev.safely.ui.detail.DetailModule
import com.kubiakdev.safely.ui.detailedition.DetailEditionFragment
import com.kubiakdev.safely.ui.detailedition.DetailEditionModule
import com.kubiakdev.safely.ui.icon.IconFragment
import com.kubiakdev.safely.ui.icon.IconModule
import com.kubiakdev.safely.ui.main.MainFragment
import com.kubiakdev.safely.ui.main.MainModule
import com.kubiakdev.safely.ui.password.PasswordFragment
import com.kubiakdev.safely.ui.password.PasswordModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

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
    @ContributesAndroidInjector(modules = [DetailEditionModule::class])
    abstract fun provideDetailEditionModuleInjector(): DetailEditionFragment

}