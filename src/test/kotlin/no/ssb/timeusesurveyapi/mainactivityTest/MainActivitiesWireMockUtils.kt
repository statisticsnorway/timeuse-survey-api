package no.ssb.timeusesurveyapi.mainactivityTest

import com.github.tomakehurst.wiremock.client.WireMock
import no.ssb.timeusesurveyapi.mainactivity.*
import java.util.UUID

internal fun stubForGetMainActivitiesGroupByDay(respondentId: UUID, payload: String) {
    WireMock.stubFor(
        WireMock.get(WireMock.urlPathMatching(getMainActivitiesGroupByDayPath(respondentId)))
            .willReturn(
                WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForGetMainActivities(respondentId: UUID, payload: String = "", statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.get(WireMock.urlPathMatching(getMainActivitiesPath(respondentId)))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForGetMainActivity(respondentId: UUID, activityId: String, payload: String) {
    WireMock.stubFor(
        WireMock.get(WireMock.urlPathMatching(getMainActivityByIdPath(respondentId, activityId)))
            .willReturn(
                WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForPostMainActivity(payload: String) {
    WireMock.stubFor(
        WireMock.post(WireMock.urlPathMatching(postMainActivityPath()))
            .withRequestBody(WireMock.containing("{"))
            .willReturn(
                WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForPostMainActivities(respondentId: UUID, payload: String) {
    WireMock.stubFor(
        WireMock.post(WireMock.urlPathMatching(postMainActivitiesPath(respondentId)))
            .withRequestBody(WireMock.containing("{"))
            .willReturn(
                WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubDeleteMainActivity(respondentId: UUID, statusCode: Int = 200){
    WireMock.stubFor(
        WireMock.delete(WireMock.urlPathMatching(deleteMainActivityPath(respondentId)))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
            )
    )
}

internal fun stubDeleteMainActivityById(respondentId: UUID, activityId: String, statusCode: Int = 200){
    WireMock.stubFor(
        WireMock.delete(WireMock.urlPathMatching(deleteMainActivityByIdPath(respondentId, activityId)))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
            )
    )
}

internal fun stubDeleteMainActivityByStartTime(respondentId: UUID, startTime: String, statusCode: Int = 200){
    WireMock.stubFor(
        WireMock.delete(WireMock.urlPathMatching(deleteMainActivityByStartTimePath(respondentId, startTime)))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
            )
    )
}

internal fun stubPatchMainActivityById(respondentId: UUID, activityId: String, statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.patch(WireMock.urlPathMatching(patchMainActivityByIdPath(respondentId, activityId)))
            .withRequestBody(WireMock.containing("{"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
            )
    )
}

//language=json
internal val mainActivityJson = """
  {
    "activity" : "Trening",
    "travelType" : "Kollektivt"
  }
""".trimIndent()

//language=json
internal val mainActivitiesJson = """
[
  $mainActivityJson,
  $mainActivityJson
]
""".trimIndent()

//language=json
internal val mainActivitiesGroupByDayJson = """
{
  "dayOneEntries" : [
    $mainActivityJson
  ],
  "dayTwoEntries" : [
    $mainActivityJson
  ]
}
""".trimIndent()