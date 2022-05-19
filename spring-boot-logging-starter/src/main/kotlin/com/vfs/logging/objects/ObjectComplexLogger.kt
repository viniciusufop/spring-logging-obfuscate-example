package com.vfs.logging.objects

import com.vfs.logging.annotations.LogFullValue
import com.vfs.logging.annotations.LogObfuscate
import com.vfs.logging.annotations.LoggingDto
import java.lang.reflect.Field

internal class ObjectComplexLogger(
    private val value: Any?
) {
    companion object {
        private const val NOT_LOGGER_INFO = "X-NOT-ATTRIBUTE-LOGGER-X"
    }

    fun test() = FieldLogger
    fun process(): String =
        when {
            value == null -> "null"
            value.javaClass.isAnnotationPresent(LoggingDto::class.java) -> processObject(value)
            else -> NOT_LOGGER_INFO
        }

    private fun processObject(obj: Any): String =
        obj.javaClass.declaredFields
            .mapNotNull { it.processField(obj) }
            .takeIf { it.isNotEmpty() }
            ?.reduce { one, two -> "$one,$two" }
            .let { "{$it}" }

    private fun Field.processField(obj: Any) =
        this.also { it.trySetAccessible() }
            .let {
                when {
                    it.isAnnotationPresent(LogFullValue::class.java) -> formatField(FieldLogger(it.get(obj)).process())
                    it.isAnnotationPresent(LogObfuscate::class.java) ->
                        formatField(FieldLogger(it.get(obj)).process(it.getAnnotation(LogObfuscate::class.java).type))
                    else -> formatField(NOT_LOGGER_INFO)
                }
            }

    private fun Field.formatField(value: String) = "\"${this.name}\"=\"$value\""
}
