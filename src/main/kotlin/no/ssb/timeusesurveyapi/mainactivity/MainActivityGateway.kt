package no.ssb.timeusesurveyapi.mainactivity

import no.ssb.timeusesurveyapi.RequestType.*
import no.ssb.timeusesurveyapi.ResponseWrapper
import no.ssb.timeusesurveyapi.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class MainActivityGateway(
    private val webClientService: WebClientService
) {

    internal fun getMainActivities(respondentId: UUID, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequest(GET, getMainActivitiesPath(respondentId), sessionTokenValue)
    }

    internal fun getMainActivityById(respondentId: UUID, activityId: String, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequest(GET, getMainActivityByIdPath(respondentId, activityId), sessionTokenValue)
    }

    internal fun getMainActivitiesGroupByDay(respondentId: UUID, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequest(GET, getMainActivitiesGroupByDayPath(respondentId), sessionTokenValue)
    }

    internal fun postMainActivity(payload: String, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(POST, postMainActivityPath(), payload, sessionTokenValue)
    }

    internal fun postMainActivities(respondentId: UUID, payload: String, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(POST, postMainActivitiesPath(respondentId), payload, sessionTokenValue)
    }

    internal fun deleteMainActivity(respondentId: UUID, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequest(DELETE, deleteMainActivityPath(respondentId), sessionTokenValue)
    }

    internal fun deleteMainActivityById(respondentId: UUID, activityId: String, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequest(DELETE, deleteMainActivityByIdPath(respondentId, activityId), sessionTokenValue)
    }

    internal fun deleteMainActivityByStartTime(respondentId: UUID, startTime: String, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequest(DELETE, deleteMainActivityByStartTimePath(respondentId, startTime), sessionTokenValue)
    }

    internal fun patchMainActivityById(respondentId: UUID, activityId: String, payload: String, sessionTokenValue: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(PATCH, patchMainActivityByIdPath(respondentId, activityId), payload, sessionTokenValue)
    }
}