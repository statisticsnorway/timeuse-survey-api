package no.ssb.timeusesurveyapi.domain.subactivity

import no.ssb.timeusesurveyapi.common.RequestType
import no.ssb.timeusesurveyapi.common.RequestWrapperWithPayload
import no.ssb.timeusesurveyapi.common.SessionToken
import no.ssb.timeusesurveyapi.common.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubActivityGateway(
    private val service: WebClientService
) {
    internal fun postSubActivity(respondentId: UUID, payload: String, sessionToken: SessionToken) =
        service.makeRequestWithPayload(
            RequestWrapperWithPayload(
                RequestType.POST,
                postSubActivityPath(respondentId),
                payload, sessionToken
            )
        )
}

internal fun postSubActivityPath(respondentId: UUID) = "/v1/respondents/$respondentId/sub-activities"