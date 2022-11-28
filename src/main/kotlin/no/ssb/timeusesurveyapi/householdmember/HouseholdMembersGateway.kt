package no.ssb.timeusesurveyapi.householdmember

import no.ssb.timeusesurveyapi.common.*
import no.ssb.timeusesurveyapi.common.RequestType.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class HouseholdMembersGateway(
    private val service: WebClientService
) {

    internal fun getHouseholdMembers(respondentId: UUID, sessionToken: SessionToken): ResponseWrapper {
        return service.makeRequest(RequestWrapper(GET, getHouseholdMembersPath(respondentId), sessionToken))
    }

    internal fun postHouseholdMembers(
        respondentId: UUID,
        payload: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return service.makeRequestWithPayload(
            RequestWrapperWithPayload(POST, postHouseholdMembersPath(respondentId), payload, sessionToken)
        )
    }

    internal fun updateHouseholdMembersById(
        respondentId: UUID,
        householdMemberId: String,
        payload: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return service.makeRequestWithPayload(
            RequestWrapperWithPayload(PUT, putHouseholdMembersByIdPath(respondentId, householdMemberId), payload, sessionToken)
        )
    }

    internal fun deleteHouseholdMembersById(
        respondentId: UUID,
        householdMemberId: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return service.makeRequest(
            RequestWrapper(DELETE, deleteHouseholdMembersByIdPath(respondentId, householdMemberId), sessionToken)
        )
    }
}