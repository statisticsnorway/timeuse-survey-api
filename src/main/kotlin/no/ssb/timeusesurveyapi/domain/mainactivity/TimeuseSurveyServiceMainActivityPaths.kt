package no.ssb.timeusesurveyapi.domain.mainactivity

import java.util.*

internal fun getMainActivitiesPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities"
internal fun getMainActivityByIdPath(respondentId: UUID, activityId: String) = "/v1/respondents/$respondentId/main-activities/$activityId"
internal fun getMainActivitiesGroupByDayPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities-group-by-day"

internal fun postMainActivityPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities"
internal fun postMainActivitiesPath(respondentId: UUID) = "/v1/main-activity/$respondentId"

internal fun deleteMainActivityPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities"
internal fun deleteMainActivityByIdPath(respondentId: UUID, activityId: String) = "/v1/respondents/$respondentId/main-activities/$activityId"
internal fun deleteMainActivityByStartTimePath(respondentId: UUID, startTime: String) = "/v1/main-activities/$respondentId/$startTime"

internal fun patchMainActivityByIdPath(respondentId: UUID, activityId: String) = "/v1/main-activity/$respondentId/$activityId"
