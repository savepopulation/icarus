package com.raqun.firstsamplefeature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raqun.icarus.annotations.Feature

@Feature("FirstFeature")
class FirstSampleFeatureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
    }
}