package com.vfs.logging.resource.advice

import com.vfs.logging.annotations.LoggingResource
import com.vfs.logging.resource.print.ResourceLoggingComponent
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.lang.reflect.Type
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
@ConditionalOnProperty(value = ["vfs.logging.resource"], havingValue = "enable")
class CustomRequestBodyAdviceAdapter(
    private val httpServletRequest: HttpServletRequest,
    private val loggingComponent: ResourceLoggingComponent
) : RequestBodyAdviceAdapter() {
    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ) = true

    override fun afterBodyRead(
        body: Any,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ) = body
        .also {
            if (parameter.method?.isAnnotationPresent(LoggingResource::class.java) == true)
                loggingComponent.logRequest(httpServletRequest, parameter, it)
        }
}
