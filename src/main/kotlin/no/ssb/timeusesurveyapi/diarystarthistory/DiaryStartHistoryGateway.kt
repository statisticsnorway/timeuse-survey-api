package no.ssb.timeusesurveyapi.diarystarthistory

import no.ssb.timeusesurveyapi.common.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class DiaryStartHistoryGateway(
    private val service: WebClientService
) {

    internal fun getDiaryStartHistories(respondentId: UUID, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(RequestType.GET, getDiaryStartHistoriesPath(respondentId), sessionToken))

    internal fun postDiaryStartHistory(respondentId: UUID, payload: String, sessionToken: SessionToken) =
        service.makeRequestWithPayload(
            RequestWrapperWithPayload(RequestType.POST, postDiaryStartHistoriesPath(), payload, sessionToken)
        )

    internal fun deleteDiaryStartHistory(respondentId: UUID, sessionToken: SessionToken) =
        service.makeRequest(RequestWrapper(RequestType.DELETE, deleteDiaryStartHistoriesPath(respondentId), sessionToken))

}
