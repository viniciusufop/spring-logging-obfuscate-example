package com.vfs.appdemo

import com.vfs.logging.annotations.LoggingResource
import com.vfs.logging.general.ObfuscateLogger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("test")
class TestResource(
    private val obfuscateLogger: ObfuscateLogger
) {
    companion object {
        private val logger = LoggerFactory.getLogger(TestResource::class.java)
    }

    @LoggingResource
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun test(@RequestBody request: TestRequest) =
        TestResponse(request)
            .also {
                logger.info(
                    "M=test generates log with obfuscate data. DTO={}",
                    obfuscateLogger.execute(request)
                )
            }

    @LoggingResource
    @PostMapping("/response-entity")
    @ResponseStatus(HttpStatus.CREATED)
    fun testWithResponseEntity(@RequestBody request: TestRequest) =
        TestResponse(request)
            .let {
                ResponseEntity
                    .created(URI.create("/test/response-entity/${it.uuid}"))
                    .body(it)
            }.also {
                logger.info(
                    "M=testWithResponseEntity generates log with obfuscate data. DTO={}",
                    obfuscateLogger.execute(request)
                )
            }

}