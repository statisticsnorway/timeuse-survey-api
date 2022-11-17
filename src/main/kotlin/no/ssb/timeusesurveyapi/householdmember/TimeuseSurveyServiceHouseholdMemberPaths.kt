package no.ssb.timeusesurveyapi.householdmember

import java.util.*

internal fun getHouseholdMembersPath(respondentId: UUID) = "/v1/respondents/$respondentId/household-members"
internal fun postHouseholdMembersPath(respondentId: UUID) = "/v1/respondents/$respondentId/household-members"
internal fun putHouseholdMembersByIdPath(respondentId: UUID, householdMemberId: String) = "/v1/respondents/$respondentId/household-members/$householdMemberId"
internal fun deleteHouseholdMembersByIdPath(respondentId: UUID, householdMemberId: String) = "/v1/respondents/$respondentId/household-members/$householdMemberId"
