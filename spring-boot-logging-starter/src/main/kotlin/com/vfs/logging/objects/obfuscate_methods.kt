package com.vfs.logging.objects

import org.apache.commons.lang3.StringUtils
import java.util.regex.Pattern

private const val CHAR_OBFUSCATE = "*"
private const val CHAR_DOT = "."
private const val CHAR_SPACE = " "
private const val CHAR_AT_SYMBOL = "@"
private const val CHAR_EMPTY = ""
private const val OVERLAY = "**********"
private const val CARD_SIZE = 16
private const val CARD_PREFIX_SHOW = 6
private const val CARD_SUFFIX_SHOW = 4
private const val PHONE_SUFFIX_SHOW = 4
private const val CPF_SIZE = 11
private const val CPF_PREFIX_OBFUSCATE_SIZE = 3
private const val CPF_SUFFIX_OBFUSCATE_START = 9

internal fun String.obfuscate(): String =
    when {
        isBlank() -> CHAR_SPACE
        length <= 2 -> StringUtils.repeat(CHAR_OBFUSCATE, length)
        else -> StringUtils.overlay(this, OVERLAY, 1, length - 1)
    }

internal fun String.obfuscateFull(): String = obfuscateLength(0)

internal fun String.obfuscateCard(): String =
    replace(CHAR_SPACE, CHAR_EMPTY)
        .let {
            when (it.length) {
                CARD_SIZE -> it.obfuscateWithPrefixAndSuffix(CARD_PREFIX_SHOW, CARD_SUFFIX_SHOW)
                else -> it.obfuscate()
            }
        }

internal fun String.obfuscateDocument(): String =
    replace(".", "")
        .replace("-", "")
        .let {
            when (it.length) {
                CPF_SIZE -> "${it.substring(0, CPF_PREFIX_OBFUSCATE_SIZE).obfuscateFull()}${
                it.substring(
                    CPF_PREFIX_OBFUSCATE_SIZE,
                    CPF_SUFFIX_OBFUSCATE_START
                )
                }${
                it.substring(CPF_SUFFIX_OBFUSCATE_START).obfuscateFull()
                }"
                else -> this
            }
        }

internal fun String.obfuscateName(): String =
    StringUtils.split(this)
        .takeIf { it.isNotEmpty() }
        ?.map { it.obfuscateLength(2) }
        ?.reduce(" ")
        ?: obfuscateLength(2)

internal fun String.obfuscatePhone(): String = obfuscateWithPrefixAndSuffix(0, PHONE_SUFFIX_SHOW)

internal fun String.obfuscateEmail(): String =
    split(Pattern.compile(CHAR_AT_SYMBOL), 2)
        .let {
            when {
                it.isEmpty() -> this
                it.size == 1 -> it[0].obfuscate()
                else -> "${it[0].obfuscateLength(2)}@${it[1].obfuscateEmailDomain()}"
            }
        }

private fun String.obfuscateEmailDomain(): String =
    indexOf(CHAR_DOT)
        .let {
            when (it) {
                -1 -> obfuscateLength(2)
                else -> obfuscateWithPrefixAndSuffix(2, length - it)
            }
        }

private fun String.obfuscateLength(show: Int) = obfuscateWithPrefixAndSuffix(show, 0)

private fun String.obfuscateWithPrefixAndSuffix(prefix: Int, suffix: Int) =
    when {
        length <= prefix + suffix -> StringUtils.repeat(CHAR_OBFUSCATE, length)
        else -> StringUtils.repeat(CHAR_OBFUSCATE, length - prefix - suffix)
            .let { StringUtils.overlay(this, it, prefix, length - suffix) }
    }

private fun List<String>.reduce(param: String) = this.reduce { a, b -> "$a$param$b" }
