package no.ssb.timeusesurveyapi.questionnaire

import com.github.tomakehurst.wiremock.client.WireMock
import java.util.*

internal fun stubForGetQuestionnaire(respondentId: UUID, questionnaireType: QuestionnaireType, payload: String, statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.get(WireMock.urlPathMatching(getQuestionnairePath(respondentId, questionnaireType)))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForPostQuestionnaire(respondentId: UUID, questionnaireType: QuestionnaireType, payload: String, statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.post(WireMock.urlPathMatching(postQuestionnairePath(respondentId, questionnaireType)))
            .withRequestBody(WireMock.containing("{"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

//language=json
internal val questionnaireJson = """
{
  "questionnaire" : "asdasd",
  "questionnaireType" : "WEBSKJEMA"
}
""".trimIndent()