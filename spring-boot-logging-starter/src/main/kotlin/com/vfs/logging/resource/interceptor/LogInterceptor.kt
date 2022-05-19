package com.vfs.logging.resource.interceptor

import com.vfs.logging.annotations.LoggingResource
import com.vfs.logging.resource.print.ResourceLoggingComponent
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.DispatcherType
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@ConditionalOnProperty(value = ["vfs.logging.resource"], havingValue = "enable")
class LogInterceptor(
    private val loggingComponent: ResourceLoggingComponent
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any) =
        true.also {
            if (DispatcherType.REQUEST.name == request.dispatcherType.name &&
                request.method.equals(HttpMethod.GET.name) &&
                (handler as HandlerMethod).method.isAnnotationPresent(LoggingResource::class.java)
            ) {
                loggingComponent.logRequest(request)
            }
        }
}
