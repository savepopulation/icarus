package com.raqun.icarus.processor

import com.google.auto.service.AutoService
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.annotations.Param
import com.raqun.icarus.processor.util.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import com.raqun.icarus.processor.DynamicFeature.IntentFeature
import com.raqun.icarus.processor.DynamicFeature.FragmentFeature

const val PACKAGE_NAME = "com.raqun.icarus"
const val DYNAMIC_START_METHOD_NAME = "dynamicStart"

@AutoService(Processor::class)
class IcarusProcessor : AbstractProcessor() {

    private var round = -1
    private var HALT = false
    private val features = arrayListOf<DynamicFeature>()

    override fun getSupportedSourceVersion(): SourceVersion? = SourceVersion.latestSupported()

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
                throw IllegalArgumentException("Feature annotation can only be used with classes!")
            }

            createFeature(element = element)
        }

        return true
    }

    private fun createFeature(element: Element) {
        val featureAnnotation = element.getAnnotation(Feature::class.java)
        val feature: DynamicFeature = when {
            element.isActivity(processingEnv) -> {
                IntentFeature(
                    featureAnnotation.name,
                    processingEnv.elementUtils.getPackageOf(element).toString(),
                    element.simpleName.toString()
                )
            }
            element.isFragment(processingEnv) -> {
                FragmentFeature(
                    featureAnnotation.name,
                    processingEnv.elementUtils.getPackageOf(element).toString(),
                    element.simpleName.toString()
                )
            }
            else -> {
                throw IllegalArgumentException("Feature annotation can only be used with classes!")
            }
        }

        /*
        val citizens: MutableList<out Element> = element.enclosedElements
        if (!citizens.isNullOrEmpty()) {
            for (citizen in citizens) {
                val param = citizen.getAnnotation(Param::class.java)
                if (!citizen.isField()) {
                    throw IllegalArgumentException("Param annotation can only be used with fields!")
                }
                if (param.key.isEmpty() || param.key.isBlank()) {
                    throw IllegalArgumentException("Param annotation key cannot be empty or blank!")
                }
                feature.addParam(param.key, citizen)
            }
        }*/

        features.add(feature)
    }

    private fun generateFiles() {
        processingEnv.log("Icarus generating files")
        features.forEach {
            FileSpec.builder(PACKAGE_NAME, it.featureName)
                .addType(it.build())
                .addImport("${PACKAGE_NAME}.core.Icarus", it.type.method)
                .build()
                .writeTo(processingEnv.filer)
        }
    }
}
