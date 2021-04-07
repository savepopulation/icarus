/*
 * App configuration
 */
object Config {
    const val applicationId = "com.raqun.icarus"
    const val minSdkVersion = Versions.minSdkVersion
    const val targetSdkVersion = Versions.targetSdkVersion
    const val compileSdkVersion = Versions.compileSdkVersion
    const val testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    const val versionCode = 8
    const val versionName = "1.0.7"
}

/*
 * Flavor Dimensions
 */
object Dimensions {
    const val default = "default"
}

/*
 * Product Flavors
 */
object Prod {
    const val versionCode = Config.versionCode
    const val versionName = Config.versionName
}

object Dev {
    private const val suffix = ".dev"
    const val versionCode = Config.versionCode * 10000
    const val versionName = Config.versionName
    const val versionNameSuffix = suffix
    const val applicationIdSuffix = suffix
}

