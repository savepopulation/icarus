package com.raqun.icarus.processor

import com.google.auto.service.AutoService
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.processor.util.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

private const val PACKAGE_NAME = "com.raqun.icarus"
private const val DYNAMIC_START_METHOD_NAME = "dynamicStart"

@AutoService(Processor::class)
class IcarusProcessor : AbstractProcessor() {

    private var round = -1
    private var HALT = false
    private val features = arrayListOf<DynamicFeature>()

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
        val feature = DynamicFeature(
            featureAnnotation.name,
            featureType,
            processingEnv.elementUtils.getPackageOf(element).toString(),
            element.simpleName.toString()
        )

        features.add(feature)
    }

    private fun generateFiles() {
        processingEnv.logError("generating files")

        features.forEach {
            processingEnv.generateFile(
                it.create(),
                it.featureName,
                PACKAGE_NAME
            )
        }
    }
}

fun DynamicFeature.create(): TypeSpec {

    return with(TypeSpec.objectBuilder(this.featureName)) {
        addSuperinterface(
            ClassName("$PACKAGE_NAME.core", "Feature")
                .plusParameter(ClassName(this@create.type.packageName, this@create.type.simpleName))
        )

        addProperty(
            PropertySpec.builder(this@create.featureName.toUpperCase(), String::class)
                .addModifiers(KModifier.PRIVATE, KModifier.CONST)
                .initializer("%S", "${this@create.packageName}.${this@create.className}")
                .build()
        )

        addProperty(
            PropertySpec.builder(
                DYNAMIC_START_METHOD_NAME,
                ClassName(this@create.packageName, this@create.className)
            ).addModifiers(KModifier.OVERRIDE).build()
        )

        build()
    }
}
