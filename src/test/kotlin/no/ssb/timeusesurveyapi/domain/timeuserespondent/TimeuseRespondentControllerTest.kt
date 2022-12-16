package no.ssb.timeusesurveyapi.domain.timeuserespondent

import no.ssb.timeusesurveyapi.stubGetRequest
import no.ssb.timeusesurveyapi.stubPutRequest
import no.ssb.timeusesurveyapi.timeuseRespondentJson
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
class TimeuseRespondentControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()
    private val sessionTokenHeader = HttpHeaders().also {
        it.set("Cookie", "sessionToken=c74c1987-b5e8-4d48-a1a7-f23f98ea7343")
    }

    @Test
    fun `Getting timeuse respondent by id should return same payload as from timeuse-survey-service`(){
        stubGetRequest(getTimeuseRespondentByIdPath(respondentId), payload = timeuseRespondentJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(timeuseRespondentJson, it.body)
        }
    }

    @Test
    fun `Getting timeuse respondent without sessionToken cookie should respond with 403`(){
        stubGetRequest(getTimeuseRespondentByIdPath(respondentId), payload = timeuseRespondentJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId",
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        ).also {
            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when getting timeuse respondent should give 404 from controller`(){
        stubGetRequest(getTimeuseRespondentByIdPath(respondentId), payload = timeuseRespondentJson, statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Updating timeuse respondent work as expected`(){
        stubPutRequest(putTimeuseRespondentByIdPath(respondentId), payload = timeuseRespondentJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId",
            HttpMethod.PUT,
            HttpEntity(timeuseRespondentJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(timeuseRespondentJson, it.body)
        }
    }
    
    @Test
    fun `Updating timeuse respondent status work as expected`(){
        stubPutRequest(putTimeuseRespondentStatusByIdPath(respondentId, Status.SURVEY, "verdi"), payload = timeuseRespondentJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/${Status.SURVEY}/verdi",
            HttpMethod.PUT,
            HttpEntity(timeuseRespondentJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(timeuseRespondentJson, it.body)
        }
    }

}