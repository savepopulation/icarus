package com.raqun.icarus.processor.util

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment

@Throws(IOException::class)
fun ProcessingEnvironment.generateFile(
    typeSpec: TypeSpec,
    fileName: String,
    packageName: String
) {
    FileSpec.builder(packageName, fileName)
        .addType(typeSpec)
        .build()
        .writeTo(this.filer)
}
