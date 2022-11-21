package no.ssb.timeusesurveyapi.mainactivity

import java.util.*

internal fun getMainActivitiesPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities"
internal fun getMainActivityByIdPath(respondentId: UUID, activityId: String) = "/v1/respondents/$respondentId/main-activities/$activityId"
internal fun getMainActivitiesGroupByDayPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities-group-by-day"

internal fun postMainActivityPath() = "/v1/main-activity"
internal fun postMainActivitiesPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities"

internal fun deleteMainActivityPath(respondentId: UUID) = "/v1/respondents/$respondentId/main-activities"
internal fun deleteMainActivityByIdPath(respondentId: UUID, activityId: String) = "/v1/respondents/$respondentId/main-activities/$activityId"
internal fun deleteMainActivityByStartTimePath(respondentId: UUID, startTime: String) = "/v1/main-activities/$respondentId/$startTime"

internal fun patchMainActivityByIdPath(respondentId: UUID, activityId: String) = "/v1/main-activity/$respondentId/$activityId"
