package com.raqun.icarus.processor.util

import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind.CLASS

fun Element.isClass() = kind == CLASS