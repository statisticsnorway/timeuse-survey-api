package no.ssb.timeusesurveyapi.householdmember

import no.ssb.timeusesurveyapi.RequestType.*
import no.ssb.timeusesurveyapi.ResponseWrapper
import no.ssb.timeusesurveyapi.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class HouseholdMembersGateway(
    private val service: WebClientService
) {

    internal fun getHouseholdMembers(respondentId: UUID, sessionTokenValue: String): ResponseWrapper {
        return service.makeRequest(GET, getHouseholdMembersPath(respondentId), sessionTokenValue)
    }

    internal fun postHouseholdMembers(respondentId: UUID, payload: String, sessionTokenValue: String): ResponseWrapper {
        return service.makeRequestWithPayload(POST, postHouseholdMembersPath(respondentId), payload, sessionTokenValue)
    }

    internal fun putHouseholdMembersById(respondentId: UUID, householdMemberId: String, payload: String, sessionTokenValue: String): ResponseWrapper {
        return service.makeRequestWithPayload(PUT, putHouseholdMembersByIdPath(respondentId, householdMemberId), payload, sessionTokenValue)
    }

    internal fun deleteHouseholdMembersById(respondentId: UUID, householdMemberId: String, sessionTokenValue: String): ResponseWrapper {
        return service.makeRequest(DELETE, deleteHouseholdMembersByIdPath(respondentId, householdMemberId), sessionTokenValue)
    }
}