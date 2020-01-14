package com.raqun.icarus.processor

data class FeatureHolder(
    val featureName: String,
    val type: FeatureType,
    val packageName: String,
    val className: String
)