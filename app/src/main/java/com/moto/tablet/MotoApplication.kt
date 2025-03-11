package com.moto.tablet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MotoApplication : Application() {
    companion object {
        const val MOTO_APP_URI = "https://developer.android.com/moto"
    }
}