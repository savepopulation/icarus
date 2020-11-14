package com.raqun.icarus.core

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

/*
 * Icarus
 *
 * A lib to generate navigation classes in multi-module projects
 *
 * Using approach to create dynamic features defined by Mario Sanoguera de Lorenzo
 * https://github.com/sanogueralorenzo
 * https://medium.com/@sanogueralorenzo
 */
object Icarus {
    /*
     * Keeps the classes
     */
    private val classes = mutableMapOf<String, Class<*>>()

    /*
     * Package name of the App to create intents
     */
    private lateinit var packageName: String

    /*
     * Icarus should be initialized globally before using generated classes
     */
    fun init(context: Context) {
        this.packageName = context.packageName
    }

    /*
     * Checks the library is initialized or not!
     */
    private fun checkIcarus() {
        if (!this::packageName.isInitialized) {
            throw IllegalStateException("Icarus is not initialized!")
        }
    }

    /*
     * Returns the Intent of Dynamic Activity Feature
     */
    private fun intentTo(className: String): Intent {
        checkIcarus()
        return Intent(Intent.ACTION_VIEW).setClassName(this.packageName, className)
    }

    /*
     * Extension function which is being called from Intent Features
     */
    fun String.createIntentFeature() = try {
        Class.forName(this).run { intentTo(this@createIntentFeature) }
    } catch (e: ClassNotFoundException) {
        null
    }

    /*
     * Extension function which is being called from Fragment Features
     */
    fun String.createFragmentFeature() = try {
        this.getFeature<Fragment>()?.newInstance()
    } catch (e: ClassNotFoundException) {
        null
    }

    /*
     * Gets the feature with the given class name
     */
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
