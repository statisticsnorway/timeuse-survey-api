package no.ssb.timeusesurveyapi.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import no.ssb.timeusesurveyapi.exceptions.MissingSessionTokenCookieException
import no.ssb.timeusesurveyapi.utils.containSessionTokenCookie
import no.ssb.timeusesurveyapi.utils.shouldHaveSessionToken
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class CustomHandlerInterceptor : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("${request.method} -> ${request.servletPath}")
        if(request.isForbidden()) throw MissingSessionTokenCookieException()
        return true
    }

    private fun HttpServletRequest.isForbidden() = shouldHaveSessionToken() && !containSessionTokenCookie()

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        logger.info("${request.method} -> ${request.servletPath} = ${response.status}")
    }
}

@Component
class InterceptorAppConfig(
    private val customHandlerInterceptor: CustomHandlerInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(customHandlerInterceptor)
    }
}