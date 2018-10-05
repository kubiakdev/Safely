package com.kubiakdev.safely.ui.main.fragment.password

import androidx.fragment.app.Fragment
import com.kubiakdev.safely.di.annotation.FragmentScope
import com.kubiakdev.safely.di.module.FragmentModule
import com.kubiakdev.safely.ui.main.fragment.main.MainFragment
import dagger.Binds
import dagger.Module

@Module(includes = [FragmentModule::class])
abstract class PasswordModule {

    @Binds
    @FragmentScope
    abstract fun fragment(fragment: PasswordFragment): Fragment
}