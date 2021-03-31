package com.raqun.icarus.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raqun.icarus.R
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.core.BuildConfig
import com.raqun.icarus.core.feature.SampleFragmentFeature

@Feature("SampleActivityFeature")
class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        if (savedInstanceState == null) {
            SampleFragmentFeature.dynamicStart?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayoutMain, it)
                    .commit()
            }
        }
    }
}
