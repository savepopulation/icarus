package com.raqun.icarus.processor

import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class IcarusProcessor : AbstractProcessor() {

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}