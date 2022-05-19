package com.vfs.logging.resource.advice

import com.vfs.logging.annotations.LoggingResource
import com.vfs.logging.resource.print.ResourceLoggingComponent
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice
@ConditionalOnProperty(value = ["vfs.logging.resource"], havingValue = "enable")
class CustomResponseBodyAdviceAdapter(
    private val loggingComponent: ResourceLoggingComponent
) : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>) = true

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ) = body
        .also {
            if (request is ServletServerHttpRequest &&
                response is ServletServerHttpResponse &&
                returnType.method?.isAnnotationPresent(LoggingResource::class.java) == true
            ) {
                loggingComponent.logResponse(
                    request.servletRequest,
                    response.servletResponse, it
                )
            }
        }
}
