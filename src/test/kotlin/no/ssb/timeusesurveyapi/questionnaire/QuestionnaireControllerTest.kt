package no.ssb.timeusesurveyapi.questionnaire

import no.ssb.timeusesurveyapi.questionnaire.QuestionnaireType.WEBSKJEMA
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
class QuestionnaireControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()

    @Test
    fun `Getting questionnaire should respond with same payload aas from timeuse-survey-service`(){
        stubForGetQuestionnaire(respondentId, WEBSKJEMA, questionnaireJson)

        restTemplate.getForEntity("/v1/respondent/$respondentId/questionnaire/$WEBSKJEMA", String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(questionnaireJson, it.body)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when getting questionnaire should give 404 from controller`(){
        stubForGetQuestionnaire(respondentId, WEBSKJEMA, questionnaireJson, statusCode = 404)

        restTemplate.getForEntity("/v1/respondent/$respondentId/questionnaire/$WEBSKJEMA", String::class.java).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Posting questionnaire should work as expected`(){
        stubForPostQuestionnaire(respondentId, WEBSKJEMA, questionnaireJson)

        restTemplate.postForEntity("/v1/respondent/$respondentId/questionnaire/${WEBSKJEMA}", questionnaireJson, String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `401 from timeuse-survey-service when posting questionnaire should give 401 from controller`(){
        stubForPostQuestionnaire(respondentId, WEBSKJEMA, questionnaireJson, statusCode = 401)

        restTemplate.postForEntity("/v1/respondent/$respondentId/questionnaire/${WEBSKJEMA}", questionnaireJson, String::class.java).also {
            assertEquals(HttpStatus.UNAUTHORIZED, it.statusCode)
        }
    }
}