package no.ssb.timeusesurveyapi.security.tokenexchange

import no.ssb.timeusesurveyapi.security.stubTokenExchange
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(port = 9999)
@ActiveProfiles("test")
class TokenExchangeControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()

    @Test
    fun `Exchanging token work as expected and respons has set-cookie sessionToken attached`(){
        stubTokenExchange(cookie = "sessionToken=123123; key2=verdi2")
        restTemplate.postForEntity(
            "/v1/token-exchange",
            TokenExchangeRequest("accesToken-123", "idToken-123", respondentId),
            TokenExchangeRequest::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assert(it.headers["set-cookie"]?.first()?.contains("sessionToken") ?: false)
        }
    }

    @Test
    fun `401 from timeuse-survey-service when exchanging token should give 401 from controller`(){
        stubTokenExchange(401)
        restTemplate.postForEntity(
            "/v1/token-exchange",
            TokenExchangeRequest("accesToken-123", "idToken-123", respondentId),
            TokenExchangeRequest::class.java
        ).also {
            assertEquals(HttpStatus.UNAUTHORIZED, it.statusCode)
        }
    }

}