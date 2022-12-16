package no.ssb.timeusesurveyapi.domain.timeuserespondent

import no.ssb.timeusesurveyapi.common.*
import no.ssb.timeusesurveyapi.common.RequestType.GET
import no.ssb.timeusesurveyapi.common.RequestType.PUT
import org.springframework.stereotype.Service
import java.util.*

@Service
class TimeuseRespondentGateway(
    private val service: WebClientService
) {
    internal fun getTimseuseRespondentById(respondentId: UUID, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(GET, getTimeuseRespondentByIdPath(respondentId), sessionToken))

    internal fun updateTimseuseRespondentById(
        respondentId: UUID,
        payload: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return service.makeRequestWithPayload(
            RequestWrapperWithPayload(PUT, putTimeuseRespondentByIdPath(respondentId), payload, sessionToken)
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
                PUT,
                putTimeuseRespondentStatusByIdPath(respondentId, status, value),
                payload,
                sessionToken
            )
        )
    }
}

internal fun getTimeuseRespondentByIdPath(respondentId: UUID) = "/v1/respondents/$respondentId"
internal fun putTimeuseRespondentByIdPath(respondentId: UUID) = "/v1/respondents/$respondentId"
internal fun putTimeuseRespondentStatusByIdPath(respondentId: UUID, status: Status, value: String) = "/v1/respondents/$respondentId/$status/$value"