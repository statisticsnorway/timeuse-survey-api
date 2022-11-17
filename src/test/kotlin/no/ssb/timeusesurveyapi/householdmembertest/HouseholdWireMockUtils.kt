package no.ssb.timeusesurveyapi.householdmembertest

import com.github.tomakehurst.wiremock.client.WireMock
import no.ssb.timeusesurveyapi.householdmember.deleteHouseholdMembersByIdPath
import no.ssb.timeusesurveyapi.householdmember.getHouseholdMembersPath
import no.ssb.timeusesurveyapi.householdmember.postHouseholdMembersPath
import no.ssb.timeusesurveyapi.householdmember.putHouseholdMembersByIdPath
import java.util.*

internal fun stubForGetHouseholdMembers(respondentId: UUID, payload: String = "", statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.get(WireMock.urlPathMatching(getHouseholdMembersPath(respondentId)))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForPostHouseholdMembers(respondentId: UUID, payload: String = "", statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.post(WireMock.urlPathMatching(postHouseholdMembersPath(respondentId)))
            .withRequestBody(WireMock.containing("{"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForPutHouseholdMembers(respondentId: UUID, householdMemberId: String, payload: String = "", statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.put(WireMock.urlPathMatching(putHouseholdMembersByIdPath(respondentId, householdMemberId)))
            .withRequestBody(WireMock.containing("{"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withBody(payload)
            )
    )
}

internal fun stubForDeleteHouseholdMembers(respondentId: UUID, householdMemberId: String, statusCode: Int = 200) {
    WireMock.stubFor(
        WireMock.delete(WireMock.urlPathMatching(deleteHouseholdMembersByIdPath(respondentId, householdMemberId)))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
            )
    )
}

//language=json
internal val householdMemberJson = """
    {
      "firstName" : "Ole",
      "relationToRespondent" : "Søsken"
    }
""".trimIndent()

//language=json
internal val householdMembersJson = """
[
  $householdMemberJson,
  $householdMemberJson
]
""".trimIndent()