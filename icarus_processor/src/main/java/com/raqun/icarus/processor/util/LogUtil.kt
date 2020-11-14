package com.raqun.icarus.processor.util

import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic.Kind.WARNING
import javax.tools.Diagnostic.Kind.ERROR
import javax.tools.Diagnostic.Kind.NOTE

/*
 * Prints an Error message!
 */
fun ProcessingEnvironment.logError(message: String?) = messager.printMessage(ERROR, message)

/*
 * Prints a warning message!
 */
fun ProcessingEnvironment.logWarning(message: String?) = messager.printMessage(WARNING, message)

/*
 * Prints an info message!
 */
fun ProcessingEnvironment.log(message: String?) = messager.printMessage(NOTE, message)