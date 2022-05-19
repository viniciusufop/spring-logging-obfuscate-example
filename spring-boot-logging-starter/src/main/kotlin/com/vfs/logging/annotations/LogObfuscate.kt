package com.vfs.logging.annotations

import com.vfs.logging.mask.ObfuscateType

/**
 * This annotation defines that your field will be print with obfuscate.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogObfuscate(
    val type: ObfuscateType = ObfuscateType.FULL
)
