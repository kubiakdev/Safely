package com.kubiakdev.safely.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubiakdev.safely.di.annotation.ViewModelKey
import com.kubiakdev.safely.ui.detail.DetailViewModel
import com.kubiakdev.safely.ui.icon.IconViewModel
import com.kubiakdev.safely.ui.main.MainViewModel
import com.kubiakdev.safely.ui.password.PasswordViewModel
import com.kubiakdev.safely.ui.template.TemplateViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @Reusable
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @Reusable
    @ViewModelKey(IconViewModel::class)
    internal abstract fun iconViewModel(viewModel: IconViewModel): ViewModel

    @Binds
    @IntoMap
    @Reusable
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @Reusable
    @ViewModelKey(PasswordViewModel::class)
    internal abstract fun passwordViewModel(viewModel: PasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @Reusable
    @ViewModelKey(TemplateViewModel::class)
    internal abstract fun templateViewModel(viewModel: TemplateViewModel): ViewModel

}