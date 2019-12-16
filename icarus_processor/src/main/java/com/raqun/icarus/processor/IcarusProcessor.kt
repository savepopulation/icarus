package com.raqun.icarus.processor

import com.google.auto.service.AutoService
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.processor.util.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class IcarusProcessor : AbstractProcessor() {

    private var round = -1
    private var HALT = false
    private val features = arrayListOf<FeatureHolder>()

    override fun getSupportedSourceVersion() = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes() = mutableSetOf(Feature::class.java.name)

    override fun process(p0: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        processingEnv.log("started")

        round++

        if (!doProcess(roundEnv)) {
            return HALT
        }

        if (roundEnv != null && roundEnv.processingOver()) {
            try {
                generateFiles()
                HALT = true
            } catch (e: IOException) {
                processingEnv.logError(e.message)
            }
        }

        return HALT
    }

    private fun doProcess(roundEnv: RoundEnvironment?): Boolean = processFeatures(roundEnv)

    private fun processFeatures(roundEnv: RoundEnvironment?): Boolean {

        if (roundEnv == null) return false

        val elements = roundEnv.getElementsAnnotatedWith(Feature::class.java)

        if (elements.isNullOrEmpty()) return true

        for (element in elements) {
            if (!element.isClass()) {
                processingEnv.logError("Feature annotation can only be used with classes!")
                return false
            }

            createFeature(element = element)
        }

        return true
    }

    private fun createFeature(element: Element) {
        val featureAnnotation = element.getAnnotation(Feature::class.java)
        val featureType: FeatureType = when {
            element.isActivity(processingEnv) -> FeatureType.INTENT
            element.isFragment(processingEnv) -> FeatureType.FRAGMENT
            else -> throw IllegalArgumentException("Feature annotation can only be used with classes!")
        }
        val feature = FeatureHolder(featureAnnotation.name, featureType)
        features.add(feature)
    }

    private fun generateFiles() {
        processingEnv.logError("genereting files")
        for (feature in features) {
            val typeSpecBuilder = TypeSpec.objectBuilder(feature.featureName)
            if (feature.type == FeatureType.INTENT) {
                typeSpecBuilder.addSuperinterface(
                    ClassName(
                        "com.raqun.icarus.core",
                        "Feature"
                    ).plusParameter(ClassName("android.content", "Intent"))
                )
            } else if (feature.type == FeatureType.FRAGMENT) {
                typeSpecBuilder.addSuperinterface(
                    ClassName(
                        "com.raqun.icarus.core",
                        "Feature"
                    )
                )
            }
            processingEnv.generateFile(
                typeSpecBuilder.build(),
                feature.featureName,
                "com.raqun.icarus"
            )
        }
    }
}