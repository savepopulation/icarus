package com.raqun.icarus

import android.app.Application
import com.raqun.icarus.core.Icarus

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Icarus.init(this.applicationContext)
    }
}
