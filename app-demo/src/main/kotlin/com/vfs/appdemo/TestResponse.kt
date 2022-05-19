package com.vfs.appdemo

import com.vfs.logging.annotations.LogFullValue
import com.vfs.logging.annotations.LogObfuscate
import com.vfs.logging.annotations.LoggingDto
import com.vfs.logging.mask.ObfuscateType
import java.util.*

@LoggingDto
data class TestResponse(
    @field:LogObfuscate(ObfuscateType.NAME)
    val name: String,
    @field:LogObfuscate(ObfuscateType.DOCUMENT)
    val document: String,
    @field:LogObfuscate(ObfuscateType.PHONE)
    val phone: String,
    @field:LogObfuscate(ObfuscateType.FULL)
    val password: String,
    @field:LogFullValue
    val description: String,
    @field:LogFullValue
    val uuid: String = UUID.randomUUID().toString()
) {
    constructor(testRequest: TestRequest) : this(
        name = testRequest.name,
        document = testRequest.document,
        phone = testRequest.phone,
        password = testRequest.password,
        description = testRequest.description
    )
}
