package com.raqun.icarus.processor.util

import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic.Kind.WARNING
import javax.tools.Diagnostic.Kind.ERROR
import javax.tools.Diagnostic.Kind.NOTE


fun ProcessingEnvironment.logError(message: String?) = messager.printMessage(ERROR, message)

fun ProcessingEnvironment.logWarning(message: String?) = messager.printMessage(WARNING, message)

fun ProcessingEnvironment.log(message: String?) = messager.printMessage(NOTE, message)