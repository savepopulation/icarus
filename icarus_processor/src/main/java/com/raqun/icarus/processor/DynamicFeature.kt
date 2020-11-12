package com.raqun.icarus.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import javax.lang.model.element.Element;

sealed class DynamicFeature {

    abstract val featureName: String

    abstract val packageName: String

    abstract val className: String

    abstract val type: Feature

    private val params: MutableMap<String, Element> = linkedMapOf()

    data class IntentFeature(
        override val featureName: String,
        override val packageName: String,
        override val className: String,
    ) : DynamicFeature() {

        override val type: Feature
            get() = Feature.intent()
    }

    data class FragmentFeature(
        override val featureName: String,
        override val packageName: String,
        override val className: String,
    ) : DynamicFeature() {

        override val type: Feature
            get() = Feature.fragment()
    }

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

    fun addParam(key: String, e: Element) {
        params[key] = e
    }

    class Feature private constructor(
        val packageName: String,
        val simpleName: String,
        val method: String
    ) {
        companion object {

            fun intent() = Feature(
                packageName = "android.content",
                simpleName = "Intent",
                method = "createIntentFeature"
            )

            fun fragment() = Feature(
                packageName = "androidx.fragment.app",
                simpleName = "Fragment",
                method = "createFragmentFeature"
            )
        }
    }
}
