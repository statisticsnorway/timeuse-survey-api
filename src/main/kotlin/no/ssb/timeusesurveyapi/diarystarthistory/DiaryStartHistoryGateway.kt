package no.ssb.timeusesurveyapi.diarystarthistory

import no.ssb.timeusesurveyapi.common.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class DiaryStartHistoryGateway(
    private val service: WebClientService
) {

    internal fun getDiaryStartHistories(respondentId: UUID, sessionToken: SessionToken): ResponseWrapper {
        return service.makeRequest(RequestWrapper(RequestType.GET, getDiaryStartHistoriesPath(respondentId), sessionToken))
    }

    fun postDiaryStartHistory(respondentId: UUID, payload: String, sessionToken: SessionToken): ResponseWrapper {
        return service.makeRequestWithPayload(
            RequestWrapperWithPayload(RequestType.POST, postDiaryStartHistoriesPath(), payload, sessionToken)
        )
    }

    fun deleteDiaryStartHistory(respondentId: UUID, sessionToken: SessionToken): ResponseWrapper {
        return service.makeRequest(RequestWrapper(RequestType.DELETE, deleteDiaryStartHistoriesPath(respondentId), sessionToken))
    }

}
