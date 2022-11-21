package no.ssb.timeusesurveyapi.householdmember

import no.ssb.timeusesurveyapi.RequestType.*
import no.ssb.timeusesurveyapi.ResponseWrapper
import no.ssb.timeusesurveyapi.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class HouseholdMembersGateway(
    private val webClientService: WebClientService
) {

    internal fun getHouseholdMembers(respondentId: UUID): ResponseWrapper {
        return webClientService.makeRequestWithoutPayload(GET, getHouseholdMembersPath(respondentId))
    }

    internal fun postHouseholdMembers(respondentId: UUID, payload: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(POST, postHouseholdMembersPath(respondentId), payload = payload)
    }

    internal fun putHouseholdMembersById(respondentId: UUID, householdMemberId: String, payload: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(PUT, putHouseholdMembersByIdPath(respondentId, householdMemberId), payload = payload)
    }

    internal fun deleteHouseholdMembersById(respondentId: UUID, householdMemberId: String): ResponseWrapper {
        return webClientService.makeRequestWithoutPayload(DELETE, deleteHouseholdMembersByIdPath(respondentId, householdMemberId))
    }

}