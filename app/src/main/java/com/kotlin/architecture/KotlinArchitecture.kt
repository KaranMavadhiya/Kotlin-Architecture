package com.kotlin.architecture

import android.app.Application
import com.kotlindemo.utils.preferences.ApplicationPreferences

class KotlinArchitecture : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationPreferences.init(this)
    }

}