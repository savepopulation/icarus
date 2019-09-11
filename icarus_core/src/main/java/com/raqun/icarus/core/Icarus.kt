package com.raqun.icarus.core

import android.content.Intent
import androidx.fragment.app.Fragment

object Icarus {

    private val classes = mutableMapOf<String, Class<*>>()

    private inline fun <reified T : Any> Any.cast() = this as? T

    private fun intentTo(className: String) = Intent(Intent.ACTION_VIEW).setClassName("package_name_here", className)

    private fun <T> String.getFeature(): Class<out T>? =
        classes.getOrPut(this) {
            try {
                Class.forName(this)
            } catch (e: ClassNotFoundException) {
                return null
            }
        }.cast()


    internal fun String.createIntentFeature() = try {
        Class.forName(this).also { intentTo(this@createIntentFeature) }
    } catch (e: ClassNotFoundException) {
        null
    }

    internal fun String.createFragmentFeature() = try {
        this.getFeature<Fragment>()?.newInstance()
    } catch (e: ClassNotFoundException) {
        null
    }

}