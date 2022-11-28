package no.ssb.timeusesurveyapi.security.tokenexchange

import no.ssb.timeusesurveyapi.common.ResponseWrapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletResponse

@Service
class TokenExchangeGateway(
    @Value("\${timeuse-survey-service-ingress}")
    private val timeuseSurveyServiceBaseUrl: String,
    @Value("\${session-token-cookie-name}")
    private val sessionTokenCookieName: String
) {
    private val webClient: WebClient = WebClient.create()
    private val logger = LoggerFactory.getLogger(this::class.java)

    internal fun exchangeToken(tokenExchangeRequest: TokenExchangeRequest, servletRespons: HttpServletResponse): ResponseWrapper {
        try {
            val respons = webClient
                .post()
                .uri("$timeuseSurveyServiceBaseUrl/v1/token-exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(tokenExchangeRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError) { Mono.empty() }
                .toEntity<String>()
                .block()

            getSessionTokenCookieIfPresent(respons)?.let {
                servletRespons.addHeader("set-cookie", it)
            }

            return ResponseWrapper(status = respons!!.statusCode, payload = respons.body)
        } catch (e: Exception) {
            logger.error("Exception for token-exchange. Exception message = '${e.message}'")
            throw e
        }
    }

    private fun getSessionTokenCookieIfPresent(respons: ResponseEntity<String>?) = respons?.headers?.get("set-cookie")?.firstOrNull { it.contains(sessionTokenCookieName) }
}
