package com.vfs.logging.annotations

/**
 * This annotation defines that your resource needs to print
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingResource
