package com.kubiakdev.safely.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubiakdev.safely.di.annotation.ViewModelKey
import com.kubiakdev.safely.ui.main.fragment.detail.DetailViewModel
import com.kubiakdev.safely.ui.main.fragment.icon.IconViewModel
import com.kubiakdev.safely.ui.main.fragment.password.PasswordViewModel
import com.kubiakdev.safely.ui.main.fragment.template.TemplateViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(com.kubiakdev.safely.ui.main.activity.MainViewModel::class)
    internal abstract fun mainActivityViewModel(
            viewModel: com.kubiakdev.safely.ui.main.activity.MainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IconViewModel::class)
    internal abstract fun iconViewModel(viewModel: IconViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(com.kubiakdev.safely.ui.main.fragment.main.MainViewModel::class)
    internal abstract fun mainViewModel(
            viewModel: com.kubiakdev.safely.ui.main.fragment.main.MainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordViewModel::class)
    internal abstract fun passwordViewModel(viewModel: PasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TemplateViewModel::class)
    internal abstract fun templateViewModel(viewModel: TemplateViewModel): ViewModel

}