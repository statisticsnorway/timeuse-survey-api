package no.ssb.timeusesurveyapi.utils

import no.ssb.timeusesurveyapi.common.SessionToken
import no.ssb.timeusesurveyapi.exceptions.MissingSessionTokenCookieException
import javax.servlet.http.HttpServletRequest

private const val sessionTokenCookieName: String = "sessionToken"

internal fun HttpServletRequest.getSessionToken() = this.cookies
    ?.firstOrNull { it.name.equals(sessionTokenCookieName) }
    ?.run {
        SessionToken(
            name = this.name,
            value = this.value
        )
    } ?: throw MissingSessionTokenCookieException()

internal fun HttpServletRequest.containSessionTokenCookie() =
    (this.cookies?.firstOrNull() { it.name.equals(sessionTokenCookieName) }) != null

internal fun HttpServletRequest.shouldHaveSessionToken() = this.servletPath.contains("/v1/respondent")