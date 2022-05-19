package com.vfs.logging.resource.print

import com.vfs.logging.objects.ObjectComplexLogger
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ResourceLoggingComponent {
    companion object {
        private val logger = LoggerFactory.getLogger(ResourceLoggingComponent::class.java)
    }

    fun logRequest(httpServletRequest: HttpServletRequest) {
        """
            "RequestInfo" {
                "URL":"${httpServletRequest.requestURI}",  
                "HTTPMethod":"${httpServletRequest.method}",  
            } 
        """.trimIndent()
            .also { logger.info(it) }
    }

    fun logRequest(
        httpServletRequest: HttpServletRequest,
        parameter: MethodParameter,
        body: Any?
    ) {
        """
            "RequestInfo" {
                "URL":"${httpServletRequest.requestURI}",  
                "HTTPMethod":"${httpServletRequest.method}", 
                "KotlinClass":"${parameter.containingClass.name}", 
                "kotlinMethod":"${parameter.method?.name}", 
                "body":${ObjectComplexLogger(body).process()} 
            } 
        """.trimIndent()
            .also { logger.info(it) }
    }

    fun logResponse(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        body: Any?
    ) {
        """
            "ResponseInfo" {
                "URL":"${httpServletRequest.requestURI}",  
                "HTTPMethod":"${httpServletRequest.method}",
                "HTTPStatus":"${httpServletResponse.status}",
                "Body":${ObjectComplexLogger(body).process()} 
            } 
        """.trimIndent()
            .also { logger.info(it) }
    }
}
