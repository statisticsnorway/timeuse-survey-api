package no.ssb.timeusesurveyapi.utils

import no.ssb.timeusesurveyapi.exceptions.MissingSessionTokenCookieException
import javax.servlet.http.HttpServletRequest

internal fun HttpServletRequest.getSessionTokenValue() =
    this.cookies?.firstOrNull() { it.name.equals("sessionToken") }?.value ?: throw MissingSessionTokenCookieException()

internal fun HttpServletRequest.containSessionTokenCookie() =
    (this.cookies?.firstOrNull() { it.name.equals("sessionToken") }) != null

internal fun HttpServletRequest.isNotTokenExchangeRequest() = !this.servletPath.contains("/token-exchange")
internal fun HttpServletRequest.isNotActuatorRequest() = !this.servletPath.contains("/actuator")