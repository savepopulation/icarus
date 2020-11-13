package com.raqun.icarus.core

import android.content.Intent
import androidx.fragment.app.Fragment

object Icarus {

    private val classes = mutableMapOf<String, Class<*>>()

    private inline fun <reified T : Any> Any.cast() = this as? T

    // TODO create a builder to take the package name!
    private fun intentTo(className: String) = Intent(Intent.ACTION_VIEW).setClassName("com.raqun.icarus.dev", className)

    private fun <T> String.getFeature(): Class<out T>? =
        classes.getOrPut(this) {
            try {
                Class.forName(this)
            } catch (e: ClassNotFoundException) {
                return null
            }
        }.cast()


    fun String.createIntentFeature() = try {
        Class.forName(this).run { intentTo(this@createIntentFeature) }
    } catch (e: ClassNotFoundException) {
        null
    }

    fun String.createFragmentFeature() = try {
        this.getFeature<Fragment>()?.newInstance()
    } catch (e: ClassNotFoundException) {
        null
    }

}