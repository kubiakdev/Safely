package com.kubiakdev.safely.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kubiakdev.safely.di.annotation.ViewModelKey
import com.kubiakdev.safely.ui.detail.DetailSharedViewModel
import com.kubiakdev.safely.ui.detail.DetailViewModel
import com.kubiakdev.safely.ui.detailedition.DetailEditionSharedViewModel
import com.kubiakdev.safely.ui.detailedition.DetailEditionViewModel
import com.kubiakdev.safely.ui.icon.IconSharedViewModel
import com.kubiakdev.safely.ui.icon.IconViewModel
import com.kubiakdev.safely.ui.main.MainSharedViewModel
import com.kubiakdev.safely.ui.main.MainViewModel
import com.kubiakdev.safely.ui.password.PasswordSharedViewModel
import com.kubiakdev.safely.ui.password.PasswordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
            factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainSharedViewModel::class)
    internal abstract fun mainSharedViewModel(viewModel: MainSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailSharedViewModel::class)
    internal abstract fun detailSharedViewModel(viewModel: DetailSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordViewModel::class)
    internal abstract fun passwordViewModel(viewModel: PasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordSharedViewModel::class)
    internal abstract fun passwordSharedViewModel(viewModel: PasswordSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailEditionViewModel::class)
    internal abstract fun detailEditionViewModel(viewModel: DetailEditionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailEditionSharedViewModel::class)
    internal abstract fun detailEditionSharedViewModel(
            viewModel: DetailEditionSharedViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IconViewModel::class)
    internal abstract fun iconViewModel(viewModel: IconViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IconSharedViewModel::class)
    internal abstract fun iconSharedViewModel(viewModel: IconSharedViewModel): ViewModel

}