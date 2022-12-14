package no.ssb.timeusesurveyapi

import com.github.tomakehurst.wiremock.client.WireMock

internal fun stubGetRequest(path: String, payload: String = "", statusCode: Int = 200, withSessionTokenCookie: Boolean = true){
    WireMock.stubFor(
        WireMock.get(WireMock.urlEqualTo(path)).also {
            if (withSessionTokenCookie) it.withCookie("sessionToken", WireMock.matching("^.{36}$"))
        }
        .willReturn(
            WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                .withBody(payload)
        )
    )
}

internal fun stubPostRequest(path: String, payload: String = "", statusCode: Int = 200){
    WireMock.stubFor(
        WireMock.post(WireMock.urlPathMatching(path))
            .withCookie("sessionToken", WireMock.matching("^.{36}$"))
            .withRequestBody(WireMock.containing("{"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubPutRequest(path: String, statusCode: Int = 200, payload: String){
    WireMock.stubFor(
        WireMock.put(WireMock.urlPathMatching(path))
            .withRequestBody(WireMock.containing("{"))
            .withCookie("sessionToken", WireMock.matching("^.{36}$"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubDeleteRequest(path: String, statusCode: Int = 200){
    WireMock.stubFor(
        WireMock.delete(WireMock.urlPathMatching(path))
            .withCookie("sessionToken", WireMock.matching("^.{36}$"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
            )
    )
}

internal fun stubPatchRequest(path: String, statusCode: Int = 200){
    WireMock.stubFor(
        WireMock.patch(WireMock.urlPathMatching(path))
            .withCookie("sessionToken", WireMock.matching("^.{36}$"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
            )
    )
}
//language=json
internal val householdMemberJson = """
    {
      "firstName" : "Ole",
      "relationToRespondent" : "S??sken"
    }
""".trimIndent()

//language=json
internal val householdMembersJson = """
[
  $householdMemberJson,
  $householdMemberJson
]
""".trimIndent()

//language=json
internal val questionnaireJson = """
{
  "questionnaire" : "asdasd",
  "questionnaireType" : "WEBSKJEMA"
}
""".trimIndent()

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

//language=json
internal val timeuseRespondentJson = """
{
  "parentName" : "Ole",
  "followUp" : true
}
""".trimIndent()

//language=json
internal val diaryStartHistoryJson = """
    {
      "diaryStart" : "2022-01-01",
      "createdTime" : "2022-01-01 11:11:11"
    }
""".trimIndent()

internal val diaryStartHistoriesJson = """
    [
        $diaryStartHistoryJson,
        $diaryStartHistoryJson,
    ]
""".trimIndent()

//language=json
internal val companionJson = """
    {
      "withFriends" : true,
      "withRelatives" : true
    }
""".trimIndent()

internal val companionsJson = """
    [
        $companionJson,
        $companionJson,
    ]
""".trimIndent()

//language=json
internal val subActivityJson = """
    {
      "startTime" : "10:00",
      "endTime" : "10:10"
    }
""".trimIndent()

//language=json
internal val surveyJson = """
    {
      "id" : 123,
      "name" : "SuRvEy"
    }
""".trimIndent()
