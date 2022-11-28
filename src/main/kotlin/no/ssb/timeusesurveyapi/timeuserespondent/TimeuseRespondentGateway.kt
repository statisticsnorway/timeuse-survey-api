package no.ssb.timeusesurveyapi.timeuserespondent

import no.ssb.timeusesurveyapi.common.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class TimeuseRespondentGateway(
    private val service: WebClientService
) {
    internal fun getTimseuseRespondentById(respondentId: UUID, sessionToken: SessionToken): ResponseWrapper {
        return service.makeRequest(
            RequestWrapper(RequestType.GET, getTimeuseRespondentByIdPath(respondentId), sessionToken)
        )
    }

    internal fun updateTimseuseRespondentById(
        respondentId: UUID,
        payload: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return service.makeRequestWithPayload(
            RequestWrapperWithPayload(RequestType.PUT, putTimeuseRespondentByIdPath(respondentId), payload, sessionToken)
        )
    }

    internal fun updateTimseuseRespondentStatusById(
        respondentId: UUID,
        status: Status,
        value: String,
        payload: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return service.makeRequestWithPayload(
            RequestWrapperWithPayload(
                RequestType.PUT,
                putTimeuseRespondentStatusByIdPath(respondentId, status, value),
                payload,
                sessionToken
            )
        )
    }
}
