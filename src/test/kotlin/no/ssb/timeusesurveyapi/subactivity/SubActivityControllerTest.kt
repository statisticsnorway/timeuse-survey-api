package no.ssb.timeusesurveyapi.subactivity

import no.ssb.timeusesurveyapi.stubPostRequest
import no.ssb.timeusesurveyapi.subActivityJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(port = 9999)
@ActiveProfiles("test")
class SubActivityControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()
    private val sessionTokenHeader = HttpHeaders().also {
        it.set("Cookie", "sessionToken=c74c1987-b5e8-4d48-a1a7-f23f98ea7343")
    }

    @Test
    fun `Posting sub activity should respond with same payload as from timeuse-survey-service`(){
        stubPostRequest(postSubActivityPath(respondentId), subActivityJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/sub-activity",
            HttpMethod.POST,
            HttpEntity(subActivityJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(subActivityJson, it.body)
        }
    }

    @Test
    fun `Posting sub activity without sessionToken cookie should respond with 403`(){
        restTemplate.exchange(
            "/v1/respondent/$respondentId/sub-activity",
            HttpMethod.POST,
            HttpEntity(subActivityJson),
            String::class.java
        ).also {
            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when posting sub activity should give 404 from controller`() {
        stubPostRequest(postSubActivityPath(respondentId), subActivityJson, statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/sub-activity",
            HttpMethod.POST,
            HttpEntity(subActivityJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }
}