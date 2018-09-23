package com.kubiakdev.safely.di.component

import com.kubiakdev.safely.app.App
import com.kubiakdev.safely.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class AppBuilder : AndroidInjector.Builder<App>()
}