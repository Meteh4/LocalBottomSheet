package com.metoly.localbottomsheet
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class LocalBottomSheetApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LocalBottomSheetApplication)
            modules(modules)
        }
    }
}