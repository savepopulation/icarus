package com.raqun.icarus.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raqun.firstsamplefeature.FirstSampleFeatureActivity
import com.raqun.icarus.R
import com.raqun.icarus.annotations.Feature
import kotlinx.android.synthetic.main.fragment_sample.*

@Feature("SampleFragmentFeature")
class SampleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sample, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            // TODO open first sample feature!
        }
    }
}