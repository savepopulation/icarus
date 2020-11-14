package com.raqun.secondsamplefeature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raqun.icarus.annotations.Feature

@Feature("SecondFeature")
class SecondSampleFeatureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}