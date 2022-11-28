package no.ssb.timeusesurveyapi.householdmembertest

import no.ssb.timeusesurveyapi.*
import no.ssb.timeusesurveyapi.householdmember.deleteHouseholdMembersByIdPath
import no.ssb.timeusesurveyapi.householdmember.getHouseholdMembersPath
import no.ssb.timeusesurveyapi.householdmember.postHouseholdMembersPath
import no.ssb.timeusesurveyapi.householdmember.putHouseholdMembersByIdPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.*
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
    private val sessionTokenHeader = HttpHeaders().also {
        it.set("Cookie", "sessionToken=c74c1987-b5e8-4d48-a1a7-f23f98ea7343")
    }

    @Test
    fun `Getting household members should respond with same payload as from timeuse-survey-service`() {
        stubGetRequest(getHouseholdMembersPath(respondentId), householdMembersJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members",
            GET, HttpEntity<String>(sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(householdMembersJson, it.body)
        }
    }

    @Test
    fun `Getting household members without sessionToken cookie should respond with 403`() {
        restTemplate.getForEntity("/v1/respondent/$respondentId/household-members", String::class.java).also {
            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when getting household members should give 404 from controller`() {
        stubGetRequest(getHouseholdMembersPath(respondentId), statusCode = 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members",
            GET, HttpEntity<String>(sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Posting household members work as expected`(){
        stubPostRequest(postHouseholdMembersPath(respondentId), householdMembersJson, statusCode = 201)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members",
            POST, HttpEntity(householdMemberJson, sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.CREATED, it.statusCode)
            assertEquals(householdMembersJson, it.body)
        }
    }

    @Test
    fun `400 from timeuse-survey-service when posting household members should give 400 from controller`(){
        stubPostRequest(postHouseholdMembersPath(respondentId), householdMembersJson, statusCode = 400)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members",
            POST, HttpEntity(householdMemberJson, sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.BAD_REQUEST, it.statusCode)
        }
    }

    @Test
    fun `Updating household members work as expected`(){
        val householdMemberId = "123"
        stubPutRequest(putHouseholdMembersByIdPath(respondentId, householdMemberId), payload = householdMemberJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            PUT, HttpEntity(householdMemberJson, sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
            assertEquals(householdMemberJson, it.body)
        }
    }

    @Test
    fun `400 from timeuse-survey-service when updating household members should give 400 from controller`(){
        val householdMemberId = "123"
        stubPutRequest(putHouseholdMembersByIdPath(respondentId, householdMemberId), 400, householdMemberJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            PUT, HttpEntity(householdMemberJson, sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.BAD_REQUEST, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when updating household members should give 404 from controller`(){
        val householdMemberId = "123"
        stubPutRequest(putHouseholdMembersByIdPath(respondentId, householdMemberId), 404, householdMemberJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            PUT, HttpEntity(householdMemberJson, sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

    @Test
    fun `Delete household member by id work as expected`(){
        val householdMemberId = "123"
        stubDeleteRequest(deleteHouseholdMembersByIdPath(respondentId, householdMemberId))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            DELETE, HttpEntity<String>(sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

    @Test
    fun `404 from timeuse-survey-service when delete household member by id should give 404 from controller`(){
        val householdMemberId = "123"
        stubDeleteRequest(deleteHouseholdMembersByIdPath(respondentId, householdMemberId), 404)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/household-members/$householdMemberId",
            DELETE, HttpEntity<String>(sessionTokenHeader), String::class.java
        ).also {
            assertEquals(HttpStatus.NOT_FOUND, it.statusCode)
        }
    }

}