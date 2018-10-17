package com.kubiakdev.safely.app

import android.content.ContextWrapper
import android.os.StrictMode
import com.kubiakdev.safely.data.db.entity.MyObjectBox
import com.kubiakdev.safely.di.component.DaggerAppComponent
import com.kubiakdev.safely.util.EventBus
import com.pixplicity.easyprefs.library.Prefs
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var eventBus: EventBus

    override fun onCreate() {
        super.onCreate()
        initDatabase()
        initPrefs()
        eventBus.send("action performed")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().create(this)

    private fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) return
        enabledStrictMode()
        LeakCanary.install(this)
    }

    private fun enabledStrictMode() =
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder() //
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())

    private fun initDatabase() {
        MyObjectBox.builder().androidContext(this).buildDefault()
//        BoxStore.getDefault().close()
//        BoxStore.getDefault().deleteAllFiles()
    }

    private fun initPrefs() {
        Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()
    }
}
