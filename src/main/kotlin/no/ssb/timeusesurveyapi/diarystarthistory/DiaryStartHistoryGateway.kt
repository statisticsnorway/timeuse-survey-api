package no.ssb.timeusesurveyapi.diarystarthistory

import no.ssb.timeusesurveyapi.common.RequestType.*
import no.ssb.timeusesurveyapi.common.RequestWrapper
import no.ssb.timeusesurveyapi.common.RequestWrapperWithPayload
import no.ssb.timeusesurveyapi.common.SessionToken
import no.ssb.timeusesurveyapi.common.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class DiaryStartHistoryGateway(
    private val service: WebClientService
) {

    internal fun getDiaryStartHistories(respondentId: UUID, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(GET, getDiaryStartHistoriesPath(respondentId), sessionToken))

    internal fun postDiaryStartHistory(respondentId: UUID, payload: String, sessionToken: SessionToken) =
        service.makeRequestWithPayload(
            RequestWrapperWithPayload(POST, postDiaryStartHistoriesPath(), payload, sessionToken)
        )

    internal fun deleteDiaryStartHistory(respondentId: UUID, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(DELETE, deleteDiaryStartHistoriesPath(respondentId), sessionToken))

}
