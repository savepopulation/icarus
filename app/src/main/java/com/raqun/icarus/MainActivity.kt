package com.raqun.icarus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raqun.icarus.core.feature.SampleActivityFeature
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonNext.setOnClickListener {
            SampleActivityFeature.dynamicStart?.let {
                startActivity(it)
            }
        }
    }
}