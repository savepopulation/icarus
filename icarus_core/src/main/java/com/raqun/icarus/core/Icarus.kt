package com.raqun.icarus.core

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

object Icarus {

    private val classes = mutableMapOf<String, Class<*>>()

    private lateinit var packageName: String

    fun init(context: Context) {
        this.packageName = context.packageName
    }

    private fun checkIcarus() {
        if (!this::packageName.isInitialized) {
            throw IllegalStateException("Icarus is not initialized!")
        }
    }

    private fun intentTo(className: String): Intent {
        checkIcarus()
        return Intent(Intent.ACTION_VIEW).setClassName(this.packageName, className)
    }

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

    private fun <T> String.getFeature(): Class<out T>? =
        classes.getOrPut(this) {
            try {
                Class.forName(this)
            } catch (e: ClassNotFoundException) {
                return null
            }
        }.cast()
}

internal inline fun <reified T : Any> Any.cast() = this as? T
