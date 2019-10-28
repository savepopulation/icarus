package com.raqun.icarus.processor

import com.google.auto.service.AutoService
import com.raqun.icarus.annotations.Feature
import com.raqun.icarus.processor.util.isClass
import com.raqun.icarus.processor.util.logError
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


@AutoService(Processor::class)
class IcarusProcessor : AbstractProcessor() {

    private var round = -1
    private var HALT = false

    override fun getSupportedSourceVersion() = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes() = mutableSetOf(Feature::class.java.name)

    override fun process(p0: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        round++

        if (!doProcess(roundEnv)) {
            return HALT;
        }

        if (roundEnv != null && roundEnv.processingOver()) {
            try {
                // TODO create files
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
        }

        return true
    }
}