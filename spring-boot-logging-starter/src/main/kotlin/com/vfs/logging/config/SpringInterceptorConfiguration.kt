package com.vfs.logging.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.vfs.logging.resource.interceptor")
class SpringInterceptorConfiguration