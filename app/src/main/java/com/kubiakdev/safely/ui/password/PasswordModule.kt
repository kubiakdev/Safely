package com.kubiakdev.safely.ui.password

import androidx.fragment.app.Fragment
import com.kubiakdev.safely.di.annotation.FragmentScope
import com.kubiakdev.safely.di.module.FragmentModule
import dagger.Binds
import dagger.Module

@Module(includes = [FragmentModule::class])
abstract class PasswordModule {

    @Binds
    @FragmentScope
    abstract fun fragment(fragment: PasswordFragment): Fragment
}