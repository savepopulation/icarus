package com.raqun.icarus.processor

enum class FeatureType(val packageName: String, val simpleName: String) {
    INTENT("android.content", "Intent"),
    FRAGMENT("androidx.fragment.app", "Fragment")
}