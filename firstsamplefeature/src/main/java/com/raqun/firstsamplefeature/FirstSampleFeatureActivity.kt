package com.raqun.firstsamplefeature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.core.feature.SecondFeature
import kotlinx.android.synthetic.main.activity_first.*

@Feature("FirstFeature")
class FirstSampleFeatureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        buttonNext.setOnClickListener {
            startActivity(SecondFeature.dynamicStart)
        }
    }
}