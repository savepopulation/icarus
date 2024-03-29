package com.raqun.icarus.processor

import com.google.auto.service.AutoService
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.processor.util.*
import com.squareup.kotlinpoet.*
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import com.raqun.icarus.processor.DynamicFeature.IntentFeature
import com.raqun.icarus.processor.DynamicFeature.FragmentFeature
import java.io.File
import java.nio.file.Paths

/*
 * Icarus Processor
 * Processes the Icarus annotations and generates the code
 */
@AutoService(Processor::class)
class IcarusProcessor : AbstractProcessor() {
    /*
     * Counts the round of processing
     */

    private var round = -1

    /*
     * Result of Process
     */
    private var result = false

    /*
     * Dynamic Features of Project
     */
    private val features = mutableListOf<DynamicFeature>()

    override fun getSupportedSourceVersion(): SourceVersion? = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes() = mutableSetOf(Feature::class.java.name)

    override fun process(p0: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        processingEnv.log("icarus started")

        round++

        if (!doProcess(roundEnv)) {
            return result
        }

        if (roundEnv != null && roundEnv.processingOver()) {
            try {
                generateFiles()
                result = true
            } catch (e: IOException) {
                processingEnv.logError(e.message)
            }
        }

        return result
    }

    private fun doProcess(roundEnv: RoundEnvironment?): Boolean = processFeatures(roundEnv)

    private fun processFeatures(roundEnv: RoundEnvironment?): Boolean {

        if (roundEnv == null) return false

        val elements = roundEnv.getElementsAnnotatedWith(Feature::class.java)

        if (elements.isNullOrEmpty()) return true

        for (element in elements) {
            if (!element.isClass()) {
                throw IllegalArgumentException("Feature annotation can only be used with classes!")
            }

            createFeature(element = element)
        }

        return true
    }

    private fun createFeature(element: Element) {
        val featureAnnotation = element.getAnnotation(Feature::class.java)
        val featureName = if (featureAnnotation.name.trim().isNotEmpty()) {
            featureAnnotation.name
        } else {
            "${element.simpleName}${FEATURE_SUFFIX}"
        }

        val feature: DynamicFeature = when {
            element.isActivity(processingEnv) -> {
                IntentFeature(
                    featureName,
                    processingEnv.elementUtils.getPackageOf(element).toString(),
                    element.simpleName.toString()
                )
            }
            element.isFragment(processingEnv) -> {
                FragmentFeature(
                    featureName,
                    processingEnv.elementUtils.getPackageOf(element).toString(),
                    element.simpleName.toString()
                )
            }
            else -> {
                throw IllegalArgumentException("Feature annotation can only be used with classes!")
            }
        }

        features.add(feature)
    }

    private fun generateFiles() {
        processingEnv.log("Icarus stared generating files")
        features.forEach {
            val file =
                File(
                    Paths.get("").toAbsolutePath()
                        .toString() + "/icarus_core/src/main/java"
                )
            FileSpec.builder("$PACKAGE_NAME.core.feature", it.featureName)
                .addType(it.build())
                .addImport("${PACKAGE_NAME}.core.Icarus", it.type.method)
                .build()
                .writeTo(file)
        }
    }

    companion object {
        private const val FEATURE_SUFFIX = "Feature"
        const val PACKAGE_NAME = "com.raqun.icarus"
        const val DYNAMIC_START_METHOD_NAME = "dynamicStart"
    }
}
