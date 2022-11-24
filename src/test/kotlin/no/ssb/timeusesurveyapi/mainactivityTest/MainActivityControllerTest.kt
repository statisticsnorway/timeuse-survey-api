package no.ssb.timeusesurveyapi.mainactivityTest

import no.ssb.timeusesurveyapi.*
import no.ssb.timeusesurveyapi.mainactivity.*
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
class MainActivityControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()
    private val sessionTokenHeader = HttpHeaders().also {
        it.set("Cookie", "sessionToken=c74c1987-b5e8-4d48-a1a7-f23f98ea7343")
    }

    @Test
    fun `Getting main activities should respond with same payload as from timeuse-survey-service`() {
        stubGetRequest(getMainActivitiesPath(respondentId), mainActivitiesJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivitiesJson, it.body)
        }
    }

    @Test
    fun `Getting main activities without sessionToken cookie should respond with 403`() {
        stubGetRequest(getMainActivitiesPath(respondentId), mainActivitiesJson)

        restTemplate.getForEntity("/v1/respondent/$respondentId/main-activity", String::class.java).also {
            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when getting main activities should give 404 from controller`() {
        stubGetRequest(getMainActivitiesPath(respondentId), statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Getting main activity by id should respond with same payload as from timeuse-survey-service`() {
        val activityId = "123"
        stubGetRequest(getMainActivityByIdPath(respondentId, activityId), mainActivityJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivityJson, it.body)
        }
    }

    @Test
    fun `Getting main activities group by day should respond with same payload as from timeuse-survey-service`() {
        stubGetRequest(getMainActivitiesGroupByDayPath(respondentId), mainActivitiesGroupByDayJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/group-by-day",
            HttpMethod.GET,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivitiesGroupByDayJson, it.body)
        }
    }

    @Test
    fun `Posting main activity work as expected`() {
        stubPostRequest(postMainActivityPath(), mainActivityJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity",
            HttpMethod.POST,
            HttpEntity<String>(mainActivityJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivityJson, it.body)
        }
    }

    @Test
    fun `Posting main activities work as expected`() {
        stubPostRequest(postMainActivitiesPath(respondentId), mainActivitiesJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/activities",
            HttpMethod.POST,
            HttpEntity<String>(mainActivitiesJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivitiesJson, it.body)
        }
    }

    @Test
    fun `Delete main activity work as expected`(){
        stubDeleteRequest(deleteMainActivityPath(respondentId))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity",
            HttpMethod.DELETE,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also{
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when deleting main activity should give 404 from controller`(){
        stubDeleteRequest(deleteMainActivityPath(respondentId), statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity",
            HttpMethod.DELETE,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also{
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Delete main activity by id work as expected`(){
        val activityId = "123"
        stubDeleteRequest(deleteMainActivityByIdPath(respondentId, activityId))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.DELETE,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when deleting main activity by id should give 404 from controller`(){
        val activityId = "123"
        stubDeleteRequest(deleteMainActivityByIdPath(respondentId, activityId), statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.DELETE,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Delete main activity by start time work as expected`(){
        val startTime = "01.01.2022:12:12:12"
        stubDeleteRequest(deleteMainActivityByStartTimePath(respondentId, startTime))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/start-time/$startTime",
            HttpMethod.DELETE,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when deleting main activity by start time should give 404 from controller`() {
        val startTime = "01.01.2022:12:12:12"
        stubDeleteRequest(deleteMainActivityByStartTimePath(respondentId, startTime), statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/start-time/$startTime",
            HttpMethod.DELETE,
            HttpEntity<String>(sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Patch main activity by id work as expected`() {
        val activityId = "123"
        stubPatchRequest(patchMainActivityByIdPath(respondentId, activityId))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.PATCH,
            HttpEntity<String>(mainActivityJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }


    @Test
    fun `404 from timeuse-survey-service when patching main activity by id should give 404 from controller`() {
        val activityId = "123"
        stubPatchRequest(patchMainActivityByIdPath(respondentId, activityId), statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.PATCH,
            HttpEntity<String>(mainActivityJson, sessionTokenHeader),
            String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }
 }