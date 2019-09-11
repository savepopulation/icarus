/*
 * Core Libraries
 */
object CoreLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
}

/*
 * Support Libraries
 */
object SupportLibraries {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.xVersion}"
}

/*
 * Test Libraries
 */
object TestLibraries {
    const val jUnit = "junit:junit:${Versions.jUnitVersion}"
    const val runnner = "androidx.test:runner:${Versions.testRunnerVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
}

/*
 * Common Libraries
 */
object Libraries {
    const val autoService = "com.google.auto.service:auto-service:${Versions.autoServiceVersion}"
    const val kotlinPoet = "com.squareup:kotlinpoet:${Versions.kotlinPoetVersion}"
}
