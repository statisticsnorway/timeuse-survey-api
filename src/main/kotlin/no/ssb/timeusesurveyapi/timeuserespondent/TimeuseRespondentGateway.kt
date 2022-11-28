package no.ssb.timeusesurveyapi.timeuserespondent

import no.ssb.timeusesurveyapi.RequestType
import no.ssb.timeusesurveyapi.ResponseWrapper
import no.ssb.timeusesurveyapi.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class TimeuseRespondentGateway(
    private val service: WebClientService
) {
    internal fun getTimseuseRespondentById(respondentId: UUID, sessionTokenValue: String): ResponseWrapper {
        return service.makeRequest(RequestType.GET, getTimeuseRespondentByIdPath(respondentId), sessionTokenValue)
    }

    internal fun updateTimseuseRespondentById(respondentId: UUID, payload: String, sessionTokenValue: String): ResponseWrapper {
        return service.makeRequestWithPayload(RequestType.PUT, putTimeuseRespondentByIdPath(respondentId), payload, sessionTokenValue)
    }

    internal fun updateTimseuseRespondentStatusById(
        respondentId: UUID,
        status: Status,
        value: String,
        payload: String,
        sessionTokenValue: String
    ): ResponseWrapper {
        return service.makeRequestWithPayload(
            RequestType.PUT,
            putTimeuseRespondentStatusByIdPath(respondentId, status, value),
            payload,
            sessionTokenValue
        )
    }

}
