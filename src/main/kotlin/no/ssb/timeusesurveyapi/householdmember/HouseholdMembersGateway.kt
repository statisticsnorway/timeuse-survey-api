package no.ssb.timeusesurveyapi.householdmember

import no.ssb.timeusesurveyapi.RequestType.*
import no.ssb.timeusesurveyapi.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class HouseholdMembersGateway(
    private val webClientService: WebClientService
) {

    internal fun getHouseholdMembers(
        respondentId: UUID,
        sessionTokenValue: String
    ) = webClientService.makeRequest(
        requestType = GET,
        path = getHouseholdMembersPath(respondentId),
        sessionTokenValue = sessionTokenValue
    )

    internal fun postHouseholdMembers(respondentId: UUID, payload: String, sessionTokenValue: String) =
        webClientService.makeRequestWithPayload(
            requestType = POST,
            path = postHouseholdMembersPath(respondentId),
            payload = payload,
            sessionTokenValue = sessionTokenValue
        )

    internal fun putHouseholdMembersById(
        respondentId: UUID, householdMemberId: String, payload: String, sessionTokenValue: String
    ) = webClientService.makeRequestWithPayload(
        requestType = PUT,
        path = putHouseholdMembersByIdPath(respondentId, householdMemberId),
        payload = payload,
        sessionTokenValue = sessionTokenValue
    )

    internal fun deleteHouseholdMembersById(
        respondentId: UUID, householdMemberId: String, sessionTokenValue: String
    ) = webClientService.makeRequest(
        requestType = DELETE,
        path = deleteHouseholdMembersByIdPath(respondentId, householdMemberId),
        sessionTokenValue = sessionTokenValue
    )
}