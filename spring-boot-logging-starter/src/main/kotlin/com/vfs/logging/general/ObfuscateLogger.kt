package com.vfs.logging.general

import com.vfs.logging.objects.ObjectComplexLogger
import org.springframework.stereotype.Component

/**
 * This class generates the json object with obfuscate data
 */
@Component
class ObfuscateLogger {
    fun execute(obj: Any?) =
        when (obj) {
            null -> null
            else -> ObjectComplexLogger(obj).process()
        }
}
