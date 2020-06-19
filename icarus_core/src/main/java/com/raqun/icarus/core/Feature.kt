package com.raqun.icarus.core

interface Feature<T> {
    val path: String?
    val dynamicStart: T?
}