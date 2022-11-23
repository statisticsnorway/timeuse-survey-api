package no.ssb.timeusesurveyapi.householdmembertest

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
import java.util.*

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(port = 9999)
@ActiveProfiles("test")
class HouseholdMemberControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()

    @Test
    fun `Getting household members should respond with same payload as from timeuse-survey-service`() {
        stubForGetHouseholdMembers(respondentId = respondentId, payload = householdMembersJson)

        restTemplate.getForEntity("/v1/respondent/$respondentId/household-members", String::class.java).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(householdMembersJson, it.body)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when getting household members should give 404 from controller`() {
        stubForGetHouseholdMembers(respondentId, statusCode = 404)

        restTemplate.getForEntity("/v1/respondent/$respondentId/household-members", String::class.java).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Posting household members work as expected`(){
        stubForPostHouseholdMembers(respondentId, statusCode = 201, payload = householdMembersJson)

        restTemplate.postForEntity("/v1/respondent/$respondentId/household-members", householdMembersJson, String::class.java).also {
            assertEquals(HttpStatus.CREATED, it.statusCode)
            assertEquals(householdMembersJson, it.body)
        }
    }

    @Test
    fun `400 from timeuse-survey-service when posting household members should give 400 from controller`(){
        stubForPostHouseholdMembers(respondentId, statusCode = 400, payload = householdMembersJson)

        restTemplate.postForEntity("/v1/respondent/$respondentId/household-members", householdMembersJson, String::class.java).also {
            assertEquals(HttpStatus.BAD_REQUEST, it.statusCode)
        }
    }

    @Test
    fun `Put for household members work as expected`(){
        val householdMemberId = "123"
        stubForPutHouseholdMembers(respondentId, householdMemberId, householdMemberJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            HttpMethod.PUT, HttpEntity(householdMemberJson), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(householdMemberJson, it.body)
        }
    }

    @Test
    fun `400 from timeuse-survey-service when put for household members should give 400 from controller`(){
        val householdMemberId = "123"
        stubForPutHouseholdMembers(respondentId, householdMemberId, householdMemberJson, statusCode = 400)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            HttpMethod.PUT, HttpEntity(householdMemberJson), String::class.java
        ).also {
            assertEquals(HttpStatus.BAD_REQUEST, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when put for household members should give 404 from controller`(){
        val householdMemberId = "123"
        stubForPutHouseholdMembers(respondentId, householdMemberId, householdMemberJson, statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            HttpMethod.PUT, HttpEntity(householdMemberJson), String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Delete household member by id work as expected`(){
        val householdMemberId = "123"
        stubForDeleteHouseholdMembers(respondentId, householdMemberId)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when delete household member by id should give 404 from controller`(){
        val householdMemberId = "123"
        stubForDeleteHouseholdMembers(respondentId, householdMemberId, statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

}