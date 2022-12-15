package no.ssb.timeusesurveyapi.survey

import no.ssb.timeusesurveyapi.stubGetRequest
import no.ssb.timeusesurveyapi.surveyJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(port = 9999)
@ActiveProfiles("test")
class SurveyControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `Get survey without query parameter should respond with same payload as from timeuse-survey-service`(){
        stubGetRequest(getSurveyPath(null), surveyJson, withSessionTokenCookie = false)

        restTemplate.getForEntity(
            "/v1/survey",
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(surveyJson, it.body)
        }
    }

    @Test
    fun `Get survey with query parameter should respond with same payload as from timeuse-survey-service`(){
        stubGetRequest(getSurveyPath("TBU"), surveyJson, withSessionTokenCookie = false)

        restTemplate.getForEntity(
            "/v1/survey?abbr=TBU",
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(surveyJson, it.body)
        }
    }
}