package com.raqun.icarus.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter

sealed class DynamicFeature {

    abstract val featureName: String

    abstract val packageName: String

    abstract val className: String

    abstract val type: FeatureType

    data class IntentFeature(
        override val featureName: String,
        override val packageName: String,
        override val className: String,
        override val type: FeatureType = FeatureType.INTENT
    ) : DynamicFeature()

    data class FragmentFeature(
        override val featureName: String,
        override val packageName: String,
        override val className: String,
        override val type: FeatureType = FeatureType.FRAGMENT
    ) : DynamicFeature()

    fun build(): TypeSpec {
        return with(TypeSpec.objectBuilder(this.featureName)) {
            addSuperinterface(
                ClassName("$PACKAGE_NAME.core", "Feature")
                    .plusParameter(
                        ClassName(
                            type.packageName,
                            type.simpleName
                        )
                    )
            )

            addProperty(
                PropertySpec.builder(
                    "path",
                    String::class
                ).addModifiers(KModifier.OVERRIDE)
                    .initializer("%S", "${packageName}.${className}")
                    .build()
            )

            addProperty(
                PropertySpec.builder(
                    DYNAMIC_START_METHOD_NAME,
                    ClassName(
                        type.packageName,
                        type.simpleName
                    ).copy(nullable = true)
                ).addModifiers(KModifier.OVERRIDE)
                    .initializer(CodeBlock.of("path.${type.method}()"))
                    .build()
            )

            build()
        }
    }
}
