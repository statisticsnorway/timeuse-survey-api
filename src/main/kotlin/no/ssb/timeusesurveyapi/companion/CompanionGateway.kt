package no.ssb.timeusesurveyapi.companion

import no.ssb.timeusesurveyapi.common.RequestType.DELETE
import no.ssb.timeusesurveyapi.common.RequestType.GET
import no.ssb.timeusesurveyapi.common.RequestWrapper
import no.ssb.timeusesurveyapi.common.SessionToken
import no.ssb.timeusesurveyapi.common.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CompanionGateway(
    private val service: WebClientService
) {

    internal fun getCompanions(respondentId: UUID, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(GET, getCompanionPath(respondentId), sessionToken))

    internal fun getCompanionById(respondentId: UUID, id: String, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(GET, getCompanionByIdPath(respondentId, id), sessionToken))

    internal fun deleteCompanions(respondentId: UUID, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(DELETE, deleteCompanionPath(respondentId), sessionToken))
}