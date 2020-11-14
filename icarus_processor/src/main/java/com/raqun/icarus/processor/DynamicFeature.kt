package com.raqun.icarus.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import javax.lang.model.element.Element;

/*
 * Dynamic Feature
 * Represents the features of App
 * which annotated with Icarus Feature Annotation
 */
sealed class DynamicFeature {
    /*
     * Name of Dynamic Feature
     */
    abstract val featureName: String

    /*
     * Package name of Dynamic Feature
     * This property is being used to create the path of the Dynamic Feature
     */
    abstract val packageName: String

    /*
     * Class Name of the Feature
     * This propertyis being used to create the Dynamic Feature
     */
    abstract val className: String

    /*
     * Type of the Feature
     * There're only Intent and Fragment types available for now.
     */
    abstract val type: Feature

    /*
     * Represents Activity features
     */
    data class IntentFeature(
        override val featureName: String,
        override val packageName: String,
        override val className: String,
    ) : DynamicFeature() {

        override val type: Feature
            get() = Feature.intent()
    }

    /*
     * Represents Fragment features
     */
    data class FragmentFeature(
        override val featureName: String,
        override val packageName: String,
        override val className: String,
    ) : DynamicFeature() {

        override val type: Feature
            get() = Feature.fragment()
    }

    /*
     * Builds the TypeSpec of the Dynamic Feature
     * with the given properties
     */
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

    /*
     * Feature class
     * Represents Core feature
     * Contains data for core android classes
     */
    class Feature private constructor(
        /*
         * Package name of core feature
         */
        val packageName: String,

        /*
         * Simple name of core feature
         */
        val simpleName: String,

        /*
         * Icarus's method name will used to create feature
         * and generated in Dynamic Feature
         */
        val method: String = "create${simpleName}Feature"
    ) {
        companion object {
            /*
             * Creates a default intent feature
             */
            fun intent() = Feature(
                packageName = "android.content",
                simpleName = "Intent",
            )

            /*
             * Creates a default fragment feature
             */
            fun fragment() = Feature(
                packageName = "androidx.fragment.app",
                simpleName = "Fragment",
            )
        }
    }
}
