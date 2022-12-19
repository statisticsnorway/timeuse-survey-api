package no.ssb.timeusesurveyapi.domain.mainactivity

import no.ssb.timeusesurveyapi.common.*
import no.ssb.timeusesurveyapi.common.RequestType.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class MainActivityGateway(
    private val webClientService: WebClientService
) {

    internal fun getMainActivities(respondentId: UUID, sessionToken: SessionToken): ResponseWrapper {
        return webClientService.makeRequest(RequestWrapper(GET, getMainActivitiesPath(respondentId), sessionToken))
    }

    internal fun getMainActivityById(
        respondentId: UUID,
        activityId: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return webClientService.makeRequest(
            RequestWrapper(GET, getMainActivityByIdPath(respondentId, activityId), sessionToken)
        )
    }

    internal fun getMainActivitiesGroupByDay(respondentId: UUID, sessionToken: SessionToken): ResponseWrapper {
        return webClientService.makeRequest(
            RequestWrapper(GET, getMainActivitiesGroupByDayPath(respondentId), sessionToken)
        )
    }

    internal fun postMainActivity(respondentId: UUID, payload: String, sessionToken: SessionToken): ResponseWrapper {
        return webClientService.makeRequestWithPayload(
            RequestWrapperWithPayload(POST, postMainActivityPath(respondentId), payload, sessionToken)
        )
    }

    internal fun postMainActivities(respondentId: UUID, payload: String, sessionToken: SessionToken): ResponseWrapper {
        return webClientService.makeRequestWithPayload(
            RequestWrapperWithPayload(POST, postMainActivitiesPath(respondentId), payload, sessionToken)
        )
    }

    internal fun deleteMainActivity(respondentId: UUID, sessionToken: SessionToken): ResponseWrapper {
        return webClientService.makeRequest(RequestWrapper(DELETE, deleteMainActivityPath(respondentId), sessionToken))
    }

    internal fun deleteMainActivityById(
        respondentId: UUID,
        activityId: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return webClientService.makeRequest(
            RequestWrapper(DELETE, deleteMainActivityByIdPath(respondentId, activityId), sessionToken)
        )
    }

    internal fun deleteMainActivityByStartTime(
        respondentId: UUID,
        startTime: String,
        sessionToken: SessionToken
    ): ResponseWrapper {
        return webClientService.makeRequest(
            RequestWrapper(DELETE, deleteMainActivityByStartTimePath(respondentId, startTime), sessionToken)
        )
    }

    internal fun updateMainActivityById(
        respondentId: UUID, activityId: String, payload: String, sessionToken: SessionToken
    ): ResponseWrapper {
        return webClientService.makeRequestWithPayload(
            RequestWrapperWithPayload(PATCH, patchMainActivityByIdPath(respondentId, activityId), payload, sessionToken)
        )
    }
}