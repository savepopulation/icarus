package com.raqun.icarus.core

/*
 * Defines the class is a Dynamic Feature and carries information about
 * the path of the feature and how to create the feature dynamically
 */
interface Feature<T> {
    /*
     * Path of the Feature
     */
    val path: String?

    /*
     * Methods that creates feature
     */
    val dynamicStart: T?
}
