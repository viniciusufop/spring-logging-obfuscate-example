package com.vfs.logging.annotations

/**
 * This annotation defines that your field will be print without obfuscate.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogFullValue
