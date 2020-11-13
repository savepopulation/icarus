package com.raqun.icarus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.core.Icarus

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
