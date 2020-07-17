package com.raqun.icarus.processor.util

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.type.TypeMirror

/*
 * Checks if the annotated class is a Fragment
 */
fun TypeMirror?.isFragment(processingEnvironment: ProcessingEnvironment): Boolean {
    if (this == null) return false
    val fragmentTypeMirror = processingEnvironment.elementUtils
        .getTypeElement("androidx.fragment.app.Fragment").asType()
    return processingEnvironment.typeUtils.isAssignable(this, fragmentTypeMirror)
}

/*
 * Checks if the annotated class is an Activity
 */
fun TypeMirror?.isActivity(processingEnvironment: ProcessingEnvironment): Boolean {
    if (this == null) return false
    val activityTypeMirror = processingEnvironment.elementUtils
        .getTypeElement("android.app.Activity").asType()
    return processingEnvironment.typeUtils.isAssignable(this, activityTypeMirror)
}

/*
 * Checks if class implements Serializable
 */
fun TypeMirror?.isSerializable(processingEnvironment: ProcessingEnvironment): Boolean {
    if (this == null) return false
    val serializableTypeMirror =
        processingEnvironment.elementUtils.getTypeElement("java.io.Serializable").asType()
    return processingEnvironment.typeUtils.isAssignable(this, serializableTypeMirror)
}

/*
 * Checks if class implements Parcelable
 */
fun TypeMirror?.isParcelable(processingEnvironment: ProcessingEnvironment): Boolean {
    if (this == null) return false
    val parcelableTypeMirror =
        processingEnvironment.elementUtils.getTypeElement("android.os.Parcelable").asType()
    return processingEnvironment.typeUtils.isAssignable(this, parcelableTypeMirror)
}
