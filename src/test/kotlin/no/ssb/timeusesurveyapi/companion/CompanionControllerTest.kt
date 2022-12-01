package no.ssb.timeusesurveyapi.companion

import no.ssb.timeusesurveyapi.companionJson
import no.ssb.timeusesurveyapi.companionsJson
import no.ssb.timeusesurveyapi.stubDeleteRequest
import no.ssb.timeusesurveyapi.stubGetRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
class CompanionControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()
    private val sessionTokenHeader = HttpHeaders().also {
        it.set("Cookie", "sessionToken=c74c1987-b5e8-4d48-a1a7-f23f98ea7343")
    }

    @Test
    fun `Getting companions should respond with same payload as from timeuse-survey-service`(){
        stubGetRequest(getCompanionPath(respondentId), companionsJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/companion",
            HttpMethod.GET, HttpEntity<String>(sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(companionsJson, it.body)
        }
    }

    @Test
    fun `Getting companions without sessionToken cookie should respond with 403`(){
        restTemplate.exchange(
            "/v1/respondent/$respondentId/companion",
            HttpMethod.GET, HttpEntity.EMPTY, String::class.java
        ).also {
            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    fun `Getting companion by id should respond with same payload as from timeuse-survey-service`(){
        val id = "123"
        stubGetRequest(getCompanionByIdPath(respondentId, id), companionJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/companion/$id",
            HttpMethod.GET, HttpEntity<String >(sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(companionJson, it.body)
        }
    }

    @Test
    fun `Deleting companions work as expected`(){
        assertTrue(false)
        stubDeleteRequest(deleteCompanionPath(respondentId))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/companion",
            HttpMethod.DELETE, HttpEntity<String>(sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }
}