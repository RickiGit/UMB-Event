package com.ricki.umbevents

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.ricki.umbevents.Helper.SettingsHelper

val settings: SettingsHelper by lazy {
    App.settings!!
}

val mResources: Resources by lazy {
    App.mResources!!
}

class App : MultiDexApplication() {
    companion object {
        var settings: SettingsHelper? = null
        var mResources: Resources? = null
    }

    override fun onCreate() {
        settings = SettingsHelper(applicationContext)
        mResources = resources
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}