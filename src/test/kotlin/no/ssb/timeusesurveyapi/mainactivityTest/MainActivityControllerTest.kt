package no.ssb.timeusesurveyapi.mainactivityTest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.util.UUID

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(port = 9999)
@ActiveProfiles("test")
class MainActivityControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()

    @Test
    fun `Getting main activities should respond with same payload as from external service`() {
        stubForGetMainActivities(respondentId = respondentId, payload = mainActivitiesJson)

        restTemplate.getForEntity("/v1/respondent/$respondentId/main-activity", String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivitiesJson, it.body)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when getting main activities should give 404 from controller`() {
        stubForGetMainActivities(respondentId, statusCode = 404)

        restTemplate.getForEntity("/v1/respondent/$respondentId/main-activity", String::class.java).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Getting main activity by id should respond with same payload as from timeuse-survey-service`() {
        val activityId = "123"
        stubForGetMainActivity(respondentId, activityId, mainActivityJson)

        restTemplate.getForEntity("/v1/respondent/$respondentId/main-activity/$activityId", String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivityJson, it.body)
        }
    }

    @Test
    fun `Getting main activities group by day should respond with same payload as from timeuse-survey-service`() {
        stubForGetMainActivitiesGroupByDay(respondentId, mainActivitiesGroupByDayJson)

        restTemplate.getForEntity("/v1/respondent/$respondentId/main-activity/group-by-day", String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivitiesGroupByDayJson, it.body)
        }
    }

    @Test
    fun `Posting main activity work as expected`() {
        stubForPostMainActivity(mainActivityJson)

        restTemplate.postForEntity("/v1/respondent/$respondentId/main-activity", mainActivityJson, String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivityJson, it.body)
        }
    }

    @Test
    fun `Posting main activities work as expected`() {
        stubForPostMainActivities(respondentId, mainActivitiesJson)

        restTemplate.postForEntity("/v1/respondent/$respondentId/main-activity/activities", mainActivitiesJson, String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(mainActivitiesJson, it.body)
        }
    }

    @Test
    fun `Delete main activity work as expected`(){
        stubDeleteMainActivity(respondentId)

        restTemplate.exchange("/v1/respondent/$respondentId/main-activity", HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java).also{
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when deleting main activity should give 404 from controller`(){
        stubDeleteMainActivity(respondentId, statusCode = 404)

        restTemplate.exchange("/v1/respondent/$respondentId/main-activity", HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java).also{
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Delete main activity by id work as expected`(){
        val activityId = "123"
        stubDeleteMainActivityById(respondentId, activityId)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when deleting main activity by id should give 404 from controller`(){
        val activityId = "123"
        stubDeleteMainActivityById(respondentId, activityId, statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Delete main activity by start time work as expected`(){
        val startTime = "01.01.2022:12:12:12"
        stubDeleteMainActivityByStartTime(respondentId, startTime)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/start-time/$startTime",
            HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when deleting main activity by start time should give 404 from controller`() {
        val startTime = "01.01.2022:12:12:12"
        stubDeleteMainActivityByStartTime(respondentId, startTime, statusCode = 404)

        restTemplate.exchange("/v1/respondent/$respondentId/main-activity/start-time/$startTime",
            HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Patch main activity work as expected`() {
        val activityId = "123"

        stubPatchMainActivityById(respondentId, activityId)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.PATCH, HttpEntity(mainActivityJson), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }


    @Test
    fun `404 from timeuse-survey-service when patching main activity should give 404 from controller`() {
        val activityId = "123"
        stubPatchMainActivityById(respondentId, activityId, statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/main-activity/$activityId",
            HttpMethod.PATCH, HttpEntity(mainActivityJson), String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

}