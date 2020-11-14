package com.raqun.icarus.annotations

/*
 * Feature
 * Use this annotation over Activities or Fragments to generate
 * navigation classes.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Feature(val name: String = "")
