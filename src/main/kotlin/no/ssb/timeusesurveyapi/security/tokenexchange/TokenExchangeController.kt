package no.ssb.timeusesurveyapi.security.tokenexchange

import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/token-exchange")
class TokenExchangeController(
    private val tokenExchangeGateway: TokenExchangeGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    internal fun exchange(
        @RequestBody tokenExchangeRequest: TokenExchangeRequest,
        servletRespons: HttpServletResponse,
    ): ResponseEntity<String> {
        logger.info("Exchanging token for respondentId='${tokenExchangeRequest.respondentId}'")
        return tokenExchangeGateway.exchangeToken(tokenExchangeRequest, servletRespons).asResponseEntity()
    }
}