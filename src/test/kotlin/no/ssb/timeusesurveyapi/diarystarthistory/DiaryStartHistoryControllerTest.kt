package no.ssb.timeusesurveyapi.diarystarthistory

import no.ssb.timeusesurveyapi.*
import org.junit.jupiter.api.Assertions
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
class DiaryStartHistoryControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    private val respondentId = UUID.randomUUID()
    private val sessionTokenHeader = HttpHeaders().also {
        it.set("Cookie", "sessionToken=c74c1987-b5e8-4d48-a1a7-f23f98ea7343")
    }

    @Test
    fun `Getting diary start history should respond with same payload as from timeuse-survey-service`(){
        stubGetRequest(getDiaryStartHistoriesPath(respondentId), diaryStartHistoriesJson)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/diary-start-history",
            HttpMethod.GET, HttpEntity<String>(sessionTokenHeader), String::class.java
        ).also {
            Assertions.assertEquals(HttpStatus.OK, it.statusCode)
            Assertions.assertEquals(diaryStartHistoriesJson, it.body)
        }
    }

    @Test
    fun `Getting diary start history without sessionToken cookie should respond with 403`(){
        restTemplate.exchange(
            "/v1/respondent/$respondentId/diary-start-history",
            HttpMethod.GET, HttpEntity.EMPTY, String::class.java
        ).also {
            Assertions.assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    fun `Post diary start history work as expected`(){
        stubPostRequest(postDiaryStartHistoriesPath(), diaryStartHistoryJson, statusCode = 201)

        restTemplate.exchange(
            "/v1/respondent/$respondentId/diary-start-history",
            HttpMethod.POST, HttpEntity(diaryStartHistoryJson, sessionTokenHeader), String::class.java
        ).also {
            Assertions.assertEquals(HttpStatus.CREATED, it.statusCode)
            Assertions.assertEquals(diaryStartHistoryJson, it.body)
        }
    }

    @Test
    fun `Delete diary start history work as expected`(){
        stubDeleteRequest(deleteDiaryStartHistoriesPath(respondentId))

        restTemplate.exchange(
            "/v1/respondent/$respondentId/diary-start-history",
            HttpMethod.DELETE, HttpEntity(diaryStartHistoryJson, sessionTokenHeader), String::class.java
        ).also {
            Assertions.assertEquals(HttpStatus.OK, it.statusCode)
        }
    }

}