package com.raqun.secondsamplefeature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.core.feature.FirstFeature
import kotlinx.android.synthetic.main.activity_second.*

@Feature("SecondFeature")
class SecondSampleFeatureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        buttonNext.setOnClickListener {
            startActivity(FirstFeature.dynamicStart)
        }
    }
}