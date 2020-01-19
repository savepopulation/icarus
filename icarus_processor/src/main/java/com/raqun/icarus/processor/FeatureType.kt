package com.raqun.icarus.processor

enum class FeatureType(
    val packageName: String,
    val simpleName: String,
    val method: String
) {
    INTENT("android.content", "Intent", "createIntentFeature"),
    FRAGMENT("androidx.fragment.app", "Fragment", "createFragmentFeature")
}