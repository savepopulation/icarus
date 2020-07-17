package com.raqun.icarus.processor.util

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind.CLASS
import javax.lang.model.element.ElementKind.FIELD

/*
 * Checks if element is a CLASS
 */
fun Element.isClass() = kind == CLASS

/*
 * Checks if element is a Field
 */
fun Element.isField() = kind == FIELD

/*
 * Checks if Element is a type of Fragment
 */
fun Element.isFragment(processingEnvironment: ProcessingEnvironment) =
    asType().isFragment(processingEnvironment)

/*
 * Checks if Element is a type of Activity
 */
fun Element.isActivity(processingEnvironment: ProcessingEnvironment) =
    asType().isActivity(processingEnvironment)
