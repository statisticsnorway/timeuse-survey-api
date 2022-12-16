package no.ssb.timeusesurveyapi.domain.questionnaire

import no.ssb.timeusesurveyapi.domain.questionnaire.QuestionnaireType.WEBSKJEMA
import no.ssb.timeusesurveyapi.questionnaireJson
import no.ssb.timeusesurveyapi.stubGetRequest
import no.ssb.timeusesurveyapi.stubPostRequest
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
class QuestionnaireControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()
    private val sessionTokenHeader = HttpHeaders().also {
        it.set("Cookie", "sessionToken=c74c1987-b5e8-4d48-a1a7-f23f98ea7343")
    }

    @Test
    fun `Getting questionnaire should respond with same payload aas from timeuse-survey-service`(){
        stubGetRequest(getQuestionnairePath(respondentId, WEBSKJEMA), questionnaireJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/questionnaire/$WEBSKJEMA",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(questionnaireJson, it.body)
        }
    }

    @Test
    fun `Getting questionnaire without sessionToken cookie should respond with 403`(){
        stubGetRequest(getQuestionnairePath(respondentId, WEBSKJEMA))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/questionnaire/$WEBSKJEMA",
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        ).also {
            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when getting questionnaire should give 404 from controller`(){
        stubGetRequest(getQuestionnairePath(respondentId, WEBSKJEMA), questionnaireJson, 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/questionnaire/$WEBSKJEMA",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Posting questionnaire should work as expected`(){
        stubPostRequest(postQuestionnairePath(respondentId, WEBSKJEMA), questionnaireJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/questionnaire/$WEBSKJEMA",
            HttpMethod.POST,
            HttpEntity(questionnaireJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `401 from timeuse-survey-service when posting questionnaire should give 401 from controller`(){
        stubPostRequest(postQuestionnairePath(respondentId, WEBSKJEMA), questionnaireJson, 401)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/questionnaire/$WEBSKJEMA",
            HttpMethod.POST,
            HttpEntity(questionnaireJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.UNAUTHORIZED, it.statusCode)
        }
    }
}