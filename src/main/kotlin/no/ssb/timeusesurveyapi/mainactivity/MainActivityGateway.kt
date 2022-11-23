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

    internal fun getMainActivities(respondentId: UUID): ResponseWrapper {
        return webClientService.makeRequest(GET, getMainActivitiesPath(respondentId))
    }

    internal fun getMainActivityById(respondentId: UUID, activityId: String): ResponseWrapper {
        return webClientService.makeRequest(GET, getMainActivityByIdPath(respondentId, activityId))
    }

    internal fun getMainActivitiesGroupByDay(respondentId: UUID): ResponseWrapper {
        return webClientService.makeRequest(GET, getMainActivitiesGroupByDayPath(respondentId))
    }

    internal fun postMainActivity(payload: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(POST, postMainActivityPath(), payload)
    }

    internal fun postMainActivities(respondentId: UUID, payload: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(POST, postMainActivitiesPath(respondentId), payload)
    }

    internal fun deleteMainActivity(respondentId: UUID): ResponseWrapper {
        return webClientService.makeRequest(DELETE, deleteMainActivityPath(respondentId))
    }

    internal fun deleteMainActivityById(respondentId: UUID, activityId: String): ResponseWrapper {
        return webClientService.makeRequest(DELETE, deleteMainActivityByIdPath(respondentId, activityId))
    }

    internal fun deleteMainActivityByStartTime(respondentId: UUID, startTime: String): ResponseWrapper {
        return webClientService.makeRequest(DELETE, deleteMainActivityByStartTimePath(respondentId, startTime))
    }

    internal fun patchMainActivityById(respondentId: UUID, activityId: String, payload: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(PATCH, patchMainActivityByIdPath(respondentId, activityId), payload)
    }
}