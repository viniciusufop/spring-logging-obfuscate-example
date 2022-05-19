package com.vfs.logging.objects

import com.vfs.logging.mask.ObfuscateType
import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FieldLogger(
    private val value: Any?
) {
    companion object {
        private val decimalFormat = DecimalFormat("###.0000")
    }

    fun process(type: ObfuscateType? = null): String =
        when {
            value == null -> "null"
            value.javaClass.isPrimitive -> value.toString().obfuscate(type)
            value.javaClass.isEnum -> value.toString().obfuscate(type)
            value is Boolean -> value.toString().obfuscate(type)
            value is String -> value.obfuscate(type)
            value is Number -> decimalFormat.format(value).obfuscate(type)
            value is BigDecimal -> decimalFormat.format(value).obfuscate(type)
            value is LocalDate -> value.format(DateTimeFormatter.ISO_LOCAL_DATE).obfuscate(type)
            value is LocalDateTime -> value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).obfuscate(type)
            value is Collection<*> -> generateCollectionObjects(value).obfuscate(type)
            else -> ObjectComplexLogger(value).process()
        }

    private fun generateCollectionObjects(fieldValue: Collection<*>) =
        fieldValue
            .map { FieldLogger(it).process() }
            .takeIf { it.isNotEmpty() }
            ?.reduce { one, two -> "$one,$two" }
            .let { "[$it]" }

    private fun String.obfuscate(obfuscateType: ObfuscateType?): String =
        when (obfuscateType) {
            null -> this
            ObfuscateType.FULL -> obfuscateFull()
            ObfuscateType.DEFAULT -> obfuscate()
            ObfuscateType.CARD -> obfuscateCard()
            ObfuscateType.NAME -> obfuscateName()
            ObfuscateType.PHONE -> obfuscatePhone()
            ObfuscateType.EMAIL -> obfuscateEmail()
            ObfuscateType.DOCUMENT -> obfuscateDocument()
        }
}
