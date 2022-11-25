package no.ssb.timeusesurveyapi.interceptor

import no.ssb.timeusesurveyapi.exceptions.MissingSessionTokenCookieException
import no.ssb.timeusesurveyapi.utils.containSessionTokenCookie
import no.ssb.timeusesurveyapi.utils.isNotActuatorRequest
import no.ssb.timeusesurveyapi.utils.isNotFaviconRequest
import no.ssb.timeusesurveyapi.utils.isNotTokenExchangeRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomHandlerInterceptor : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("${request.method} -> ${request.servletPath}")
        if(request.isForbidden()) throw MissingSessionTokenCookieException()
        return true
    }

    private fun HttpServletRequest.isForbidden() = isNotTokenExchangeRequest() && isNotActuatorRequest() && isNotFaviconRequest() && !containSessionTokenCookie()

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